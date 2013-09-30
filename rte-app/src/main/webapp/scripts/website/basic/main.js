$(document).ready(function() {

	var timeserie = new Calendar.timeserie({
		time:function(d){ return new Date(d.logDate); }
		, indicator: function(d){ return d.taux_co2;  }
		, indicatorAggregation: d3.mean
	});

	var myCalendar = Calendar({width: $(".container").width()}).timeserie(timeserie);

	d3.json("mokup/2013_day.json", function(raw){
		myCalendar.data(raw).createTiles(2013);
	});
	
});