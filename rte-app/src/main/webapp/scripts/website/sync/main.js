$(document).ready(function() {
	
	var timeserie = new Calendar.timeserie({
		time:function(d){ return new Date(d.logDate); }
		, indicator: function(d){ return d.taux_co2;  }
		, indicatorAggregation: d3.mean
	});

	var config = {
		adaptiveHeight : true
		, animation: true
		, width: $(".container").width()
	};

	var myCalendar = Calendar(config).timeserie(timeserie);

	d3.json("mokup/2011_quarter.json", function(raw){

		myCalendar.data(raw).createTiles(2011);
		
		setTimeout(function(){ 
			myCalendar.renderer("month").color(colorbrewer["YlGnBu"][6]).createTiles(2011, [4,5,6]); 
		},2000);
		setTimeout(function(){ 
			myCalendar.renderer("week").color(colorbrewer["PuBuGn"][7]).createTiles(2011, 23); }
		,4000);
		setTimeout(function(){ 
			myCalendar.renderer("week").color(colorbrewer["Oranges"][8]).createTiles(2011, 22); }
		,6000);
		setTimeout(function(){ 
			myCalendar.renderer("day").color(colorbrewer["Greens"][9]).createTiles(2011, 23, 0); }
		,8000);
	});
});