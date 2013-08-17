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


		$.ready = function(){
			
			var rte_ws = new RteWS({
				url : "192.168.1.101:8080"
			});

			var possible_displays = new RteCalendar({
				rte_ws : rte_ws
			});



			var selected_display = "taux_co2";
			var displays = ["taux_co2", "consommation", "taux_co2_consommation", "nucleaire", "nrj_ren", "fossile", "all"];
		

			createDisplaySelector(displays, selected_display);

			var display = possible_displays[selected_display];
			var agg = "day";

			/* ************************** */
			/* 
				CALENDAR INITIALIZATION 
			*/
			var calendar = new Calendar( {
				name: "Co2 produit par kWh consommé (g)"
				, renderer : new Calendar.renderer.drillthrough()
				// , buckets : display.buckets
				, colorScheme : display.colorScheme
				// , colorSchemeInverse : display.colorSchemeInverse
				, retreiveDataClosure : display.rte_datagrab_closure
				, retreiveDataCallback : display.rte_datagrab_closure(agg)
				, retreiveValueCallback : display.rte_closure
				, width : $("#container").width()
				, height: 800		
			});

			calendar.createTiles(2013);

			$("#displays").change(function(e){
				var selected_display = $("#displays").attr("value");

				var display = possible_displays[selected_display];
				calendar.name = display.name;
				calendar.colorScheme = display.colorScheme;
				calendar.colorSchemeInverse = display.colorSchemeInverse;
				calendar.retreiveValueCallback = display.rte_closure;
				calendar.retreiveDataClosure = display.rte_datagrab_closure;
				calendar.retreiveDataCallback = display.rte_datagrab_closure(agg);
				

				calendar.createTiles(2013);
			});

		
		}