$.ready = function(){
	
	var rte_ws = new RteWS({
		url : "http://192.168.1.101:8080/rte-app"
	});

	var possible_displays = new RteCalendar({
		rte_ws : rte_ws
	});



	var selected_display = "taux_co2";
	var display = possible_displays[selected_display];

	/* ************************** */
	/* 
		CALENDAR INITIALIZATION 
	*/
	var calendar = new Calendar( {
		name: display.name
		, renderer : new Calendar.renderer.drillthrough()
		, decorators: [
			new Calendar.decorator.hovered({float:'right'})
		]
		, drawHorodator: true
		, colorScheme : display.colorScheme
		, retreiveDataClosure : display.rte_datagrab_closure
		, retreiveDataCallback : display.rte_datagrab_closure("day")
		, retreiveValueCallback : display.rte_closure
		, width : $("#container").width()
		, height: 420		
	});

	calendar.createTiles(2011);
	
	$.ajax({
		url:"resources/scripts/website/index/main.js"
		, success:function(data){
			// code highlight
			$('pre code').text(data);
			$('pre code').each(function(i, e) {hljs.highlightBlock(e);});
		}
	});
	

};