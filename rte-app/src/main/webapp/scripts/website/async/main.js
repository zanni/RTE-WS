$(document).ready(function() {

	var timeserie = new Calendar.timeserie({
		time:function(d){ return new Date(d.logDate); }
		, indicator: function(d){ return d.taux_co2;  }
		, indicatorAggregation: d3.mean
	});

	var datagrab_closure = function(year){
		var me = this;
		year = year[year.length - 1];
		d3.json("mokup/"+year+"_day.json", function(raw){
			me.draw(raw,true);
		});
	};
	var config = {
		adaptiveHeight : true
		, drawLegend: true
		, animation: true
		, width: $(".container").width()
	};
	var myCalendar = Calendar(config).timeserie(timeserie).grab(datagrab_closure);

	myCalendar.createTiles([2010]);
	setTimeout(function(){ myCalendar.createTiles([2010, 2011]); },2000);
	setTimeout(function(){ myCalendar.createTiles([2010, 2011, 2012]); },4000);
	setTimeout(function(){ myCalendar.createTiles([2010, 2011, 2012,2013]); },6000);
});