$.ready = function(){
	
	// Create time series specialization callbacks
	var timeCallback = function(d){ return new Date(d.logDate); };
	var dataCallback = function(d){ return d.taux_co2;  };

	// Create time serie parser
	var parser = Calendar.data.create(timeCallback);
	var data = {};
	var datagrab_closure = function(year){
		var calendar = this;
		if(year instanceof Array){ 
			year = year[year.length - 1];
		}
		console.log(year);
		d3.json("mokup/"+year+"_day.json", function(raw){
			// parse raw data
			var grab = parser(raw);
			data = Calendar.data.merge(data, grab);
			calendar.draw(data);
		});
	}

	var valueCallback = Calendar.data.retreiveValueCallbackClosure(dataCallback, d3.mean);
	// synchronous data loading
	
	/* ************************** */
	/* 
		CALENDAR INITIALIZATION 
	*/
	var calendar = new Calendar( {
		renderer : new Calendar.renderer.year()
		, interactive : false
		, adaptiveHeight : true
		, retreiveDataCallback : datagrab_closure
		, retreiveValueCallback : valueCallback
		, upBound : 100
		, downBound : 20
		, drawLegend: false
		, drawHorodator: false
		, drawHovered: false
	
	});

	calendar.createTiles([2010]);

	setTimeout(function(){
		calendar.createTiles([2010, 2011]);
	},2000);
	setTimeout(function(){
		calendar.createTiles([2010, 2011, 2012]);
	},4000);
	setTimeout(function(){
		calendar.createTiles([2010, 2011, 2012,2013]);
	},6000);

	$.ajax({
		url : "resources/scripts/website/async/main.js",
		success : function(data) {
			// code highlight
			$('pre code').text(data);
			$('pre code').each(function(i, e) {
				hljs.highlightBlock(e);
			});
		}
	});

}