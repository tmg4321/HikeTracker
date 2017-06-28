$(document).ready(function() {
	console.log("loaded");
	display();
//	displayAddHikeForm();
});

var display = function(){
	$('#forTable').empty();
	$('#forForm').empty();
	$('#content').empty();
	$('#content').load('partials.html #statsTable', function(){
		$.ajax({
			type : 'GET',
			url : 'rest/hikes',
			dataType : 'JSON'
		}).done(function(hikes, status) {
			var totalDistance = 0;
			var totalElevation = 0;
			var totalTime = 0;
			hikes.forEach(function (hike,idx,array){
				totalDistance += hike.distance;
				totalElevation += hike.elevation;
				totalTime += hike.time;
			});
			$('#1').text('Total Distance: '+ totalDistance.toFixed(2)+' mi.');
			
			var averageDistance = (totalDistance / hikes.length);
			$('#2').text('Average Distance: '+ averageDistance.toFixed(2)+' mi.');
			
			var averageElevation = (totalElevation / hikes.length);
			$('#3').text('Average Elevation: '+ averageElevation.toFixed(2)+' feet');
			
			var averageSpeed = (totalDistance/totalTime*60);
			$('#4').text('Average Speed: '+ averageSpeed.toFixed(2)+' mph');
			
			displayAddHikeForm();
			
			displayAllHikesAndStats(hikes);
			
		}).fail(function(xhr, status, error) {
			console.error(error);
		});
	});
}

var displayAddHikeForm = function(){
	$('#forForm').load("partials.html #addHikeForm", function(){
		$('#addHikeForm input[name="submit"]').on('click', function(e){
			e.preventDefault();
			var hikeObject = {};
			var formDataArray = $(newHikeForm).serializeArray();
			$.each(formDataArray, function(){
				hikeObject[this.name]=this.value;
			});
			hikeObject.pictures = [];
			createHikeInDB(hikeObject);
		});
	});
};
	
var createHikeInDB = function(hike){
	$.ajax({
		type : 'POST',
		url : 'rest/hikes',
		datatype : 'JSON',
		contentType : 'application/json',
		data : JSON.stringify(hike)
	})
	.done(function(newHike,status){
		displayHike(newHike);
	})
	.fail(function(xhr, status, error){
		console.error(error);
	})
};

var displayHike = function(hike){
	
	$('#forTable').empty();
	$('#forForm').empty();
	$('#content').empty();
	
	var $div = $('<div>').text('Name: '+hike.name);
	$('#content').append($div);
	
	var $div1 = $('<div>').text('Distance: '+hike.distance+ ' mi.');
	$('#content').append($div1);
	var $div2 = $('<div>').text('Elevation: '+hike.elevation+ ' feet');
	$('#content').append($div2);
	var $div3 = $('<div>').text('Time: '+hike.time+ ' minutes');
	$('#content').append($div3);
	
	var $editBtn = $('<button>').text('Edit');
	$editBtn.on('click', function(e){
		e.preventDefault();
		$('#forTable').empty();
		$('#forForm').empty();
		$('#content').empty();
		$('#content').load('partials.html #editHikeForm', function(){
			$('#editHikeForm input[name="submit"]').on('click', function(e){
				e.preventDefault();
				var hikeObject = {};
				var formDataArray = $(editHikeForm).serializeArray();
				$.each(formDataArray, function(){
					hikeObject[this.name] = this.value;
				});
				updateHikeInDB(hikeObject);
			});
			$('#editHikeForm input[name="name"]').val(hike.name);
			$('#editHikeForm input[name="distance"]').val(hike.distance);
			$('#editHikeForm input[name="elevation"]').val(hike.elevation);
			$('#editHikeForm input[name="time"]').val(hike.time);
			$('#editHikeForm input[name="id"]').val(hike.id);
		});
	});
	$('#content').append($editBtn);
	
	var $statsBtn = $('<button>').text('Stats');
	$statsBtn.on('click', function(e){
		e.preventDefault();
		$('#forForm').empty();
		$('#content').empty();
		display();
	});
	$('#content').append($statsBtn);
	
	var $deleteBtn = $('<button>').text('Delete');
	$deleteBtn.on('click', function(e){
		e.preventDefault();
		$('#content').empty();
		deleteHike(hike);
		display();
	});
	$('#content').append($deleteBtn);
	
	var $picForm = $('<form id="addPicForm" name="pictureForm">');
	var $label = $('<label for="pictureForm">').text('Add a picutre url: ')
	$picForm.append($label);
	$picForm.append('<input name="url"/>');
	$picForm.append('<input type="submit" name="submit"/>').on('click', function(e){
		e.preventDefault();
		var pic = {};
		pic.url = $(pictureForm.url).val();
		hike.pictures.push(pic);
		updateHikeInDB(hike);
	});
	$('#content').append($picForm);
	
	if (hike.pictures) {
		hike.pictures.forEach(function(pic,idx,arr){
			var $div = $('<div>');
			var $img = $('<img>').attr("src",pic.url);
			$div.append($img)
			$('#forTable').append($div)
		});
	}
};

var addPictureToDataBase = function(pic) {
	$.ajax({
		type : 'GET',
		url : 'rest/hikes/'+ pic.hike.id,
	}).done(function(hike, status){
		var pics = [ pic ];
		hike.pictures = pics;
		updateHikeInDB(hike);
	})
};

var deleteHike = function(hike){
	if (confirm("Are you sure you want to delete "+ hike.name + "?")){
		$.ajax({
			type : 'DELETE',
			url : 'rest/hikes/' + hike.id,
		}).done(function(data, status) {
			display();
		}).fail(function(xhr, status, error) {
			console.error(error);
		});
	}
};

var updateHikeInDB = function(hike){
	$.ajax({
		type : 'PUT',
		url : 'rest/hikes/' + hike.id,
		dataType : 'JSON',
		contentType : 'application/json',
		data : JSON.stringify(hike)
	}).done(function(hike, status) {
		displayHike(hike);
	}).fail(function(xhr, status, error) {
		console.error(error);
	});
};

var displayAllHikesAndStats = function(hikes){
	$('#forTable').empty();
	hikes.sort(function(a,b){
		return a.id > b.id ? -1 : 1;
	});
	var $table = $('<table>');
	var $thead = $('<thead>');
	var $tr = $('<tr>');
	var $th1 = $('<th>').text('Name'); // column heading
	var $th2 = $('<th>').text('Distance'); // column heading
	var $th3 = $('<th>').text('Elevation'); // column heading
	var $th4 = $('<th>').text('Time'); // column heading
	$tr.append($th1);
	$tr.append($th2);
	$tr.append($th3);
	$tr.append($th4);
	$thead.append($tr);
	$table.append($thead);

	var $tbody = $('<tbody>');
	hikes.forEach(function(hike, idx, array) {
		var $tr = $('<tr>');
		
		$tr.on('click', function(e){
			displayHike(hike);
		});
		if (idx % 2 === 0)
			$tr.attr("bgColor", "#dee9ff"); // zebra stripe the table
		var $td1 = $('<td>');
		$td1.text(hike.name);
		$tr.append($td1);

		var $td2 = $('<td>');
		$td2.text(hike.distance);
		$tr.append($td2);
		
		var $td3 = $('<td>');
		$td3.text(hike.elevation);
		$tr.append($td3);
		
		var $td4 = $('<td>');
		$td4.text(hike.time);
		$tr.append($td4);
		
		$tbody.append($tr);
		$table.append($tbody);
	});
	$('#forTable').append($table);
};


