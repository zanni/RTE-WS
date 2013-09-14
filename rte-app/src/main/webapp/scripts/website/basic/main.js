$.ready = function() {
	// Create time series specialization callbacks
	var timeCallback = function(d) {
		return new Date(d.date);
	};
	var dataCallback = function(d) {
		return d.tauxDeCo2;
	};

	// Create time serie parser
	var parser = Calendar.data.create(timeCallback);

	// synchronous data loading
	d3.json("resources/mokup/rte.json", function(raw) {
		// parse raw data
		data = parser(raw.mixenergies.list);

		// Create valueCallback
		var valueCallback = Calendar.data.retreiveValueCallbackClosure(
				dataCallback, d3.mean);

		var calendar = new Calendar({
			name : "Basic example",
			data : data,
			renderer : new Calendar.renderer.year(),
			retreiveValueCallback : valueCallback,
			upBound : d3.max(raw.mixenergies.list, dataCallback),
			downBound : d3.min(raw.mixenergies.list, dataCallback),
			width : $("#container").width(),
			height : 400
		});

		calendar.createTiles(2013);

		setTimeout(function() {
			calendar.renderer = new Calendar.renderer.month();
			calendar.createTiles(2013, [ 4, 5, 6 ]);
		}, 2000);

		setTimeout(function() {
			calendar.renderer = new Calendar.renderer.week();
			calendar.createTiles(2013, 23);
		}, 4000);
		setTimeout(function() {
			calendar.renderer = new Calendar.renderer.week();
			calendar.createTiles(2013, 22);
		}, 6000);
		setTimeout(function() {
			calendar.renderer = new Calendar.renderer.day();
			calendar.createTiles(2013, 22, 0);
		}, 6000);
	});
	$.ajax({
		url : "resources/scripts/website/basic/main.js",
		success : function(data) {
			// code highlight
			$('pre code').text(data);
			$('pre code').each(function(i, e) {
				hljs.highlightBlock(e);
			});
		}
	});
}