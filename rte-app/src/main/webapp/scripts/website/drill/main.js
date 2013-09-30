$(document).ready(
		function() {

			var timeserie = new Calendar.timeserie({
				time : function(d) {
					return new Date(d.logDate);
				},
				indicator : function(d) {
					return d.taux_co2;
				},
				indicatorAggregation : d3.mean
			});

			var config = {
				adaptiveHeight : true,
				animation : true,
				interactive : true,
				drawLegend : true,
				width: $(".container").width()
			};
			var myCalendar = Calendar(config).timeserie(timeserie).renderer(
					new Calendar.renderer.drillthrough());

			d3.json("mokup/2011_quarter.json", function(raw) {
				myCalendar.data(raw);
				myCalendar.createTiles(2011);
			});
		});