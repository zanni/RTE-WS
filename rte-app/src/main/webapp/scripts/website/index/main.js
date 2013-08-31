/* ************************** */
		/* 
			fill weeks select with week data
		*/
		function createDisplaySelector(data, selected){
			var displays = d3.select("#displays");

			var select = displays.selectAll("option")
				.data(data);

			select.enter().append("option")
				.text(function(d){
					return d;
				})
				.attr("id", function(d){ return "displays_"+d})
				.attr("value", function(d){ return d});
			select.exit().remove();
			
			$("#displays").css("display", "inline");
			
			displays.select("#displays_"+selected).attr("selected", true);	
		}
		/* ************************** */
		/* 
			fill weeks select with week data
		*/
		function createYearSelector(data, selected){
			var years = d3.select("#years");

			var select = years.selectAll("option")
				.data(data);

			select.enter().append("option")
				.text(function(d){
					return d;
				})
				.attr("id", function(d){ return "years_"+d})
				.attr("value", function(d){ return d});
			select.exit().remove();
			
			$("#years").css("display", "inline");
			
			years.select("#years_"+selected).attr("selected", true);	
		}


		$.ready = function(){
			
			var rte_ws = new RteWS({
				url : "http://192.168.122.199:8080/rte-app"
			});

			var possible_displays = new RteCalendar({
				rte_ws : rte_ws
			});



			var selected_display = "taux_co2";
			var displays = ["taux_co2", "consommation", "taux_co2_consommation", "nucleaire", "nrj_ren", "fossile", "all"];
		

			createDisplaySelector(displays, selected_display);
			

			var display = possible_displays[selected_display];
			var agg = "day";
			var selected_year = 2011;
			var years = [2010,2011, 2012, 2013];
			createYearSelector(years, selected_year);

			/* ************************** */
			/* 
				CALENDAR INITIALIZATION 
			*/
			var calendar = new Calendar( {
				name: display.name
				, renderer : new Calendar.renderer.drillthrough()
				, decorators: [ new Calendar.decorator.hovered({float:'right'}) ]
				, colorScheme : display.colorScheme
				, retreiveDataClosure : display.rte_datagrab_closure
				, retreiveDataCallback : display.rte_datagrab_closure(agg)
				, retreiveValueCallback : display.rte_closure
				, width : $("#container").width()
				, height: 600		
			});

			calendar.createTiles(selected_year);

			$("#years, #displays").change(function(e){
				var selected_display = $("#displays").attr("value");
				var years = $.map( $('#years option:selected'),
                  			function(e) { return parseInt($(e).val()) } );

				var display = possible_displays[selected_display];
				calendar.name = display.name;
				calendar.colorScheme = display.colorScheme;
				calendar.colorSchemeInverse = display.colorSchemeInverse;
				calendar.retreiveValueCallback = display.rte_closure;
				calendar.retreiveDataClosure = display.rte_datagrab_closure;
				calendar.retreiveDataCallback = display.rte_datagrab_closure(agg);
				

				calendar.createTiles(years);

				calendar.redrawLegend();
			});
	
		}