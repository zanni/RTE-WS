$(document).ready(function() {

	var timeserie = new Calendar.timeserie({
		time:function(d){ return new Date(d.logDate); }
		, indicator: function(d){ return d.taux_co2;  }
		, indicatorAggregation: d3.mean
	});
	var getRTEURl = function (agg, fields, start, end){

		if(!start || !end)return;
		var format = d3.time.format("%Y-%m-%d");
		return "http://192.168.1.103:8080/rte-app/mixenergys/"+agg+"/"
					+fields+"/between/jsonp/"
					+format(start) + "/" + format(end);
	};
	var cache = {};
	var datagrab_closure = function(){
		var me = this;
		var args = Array.prototype.slice.call(arguments);
		var bounds = me.renderer.bounds.apply(me, arguments);
		if(bounds){
			var agg = args.pop();
			var url = getRTEURl(agg, "taux_co2", bounds.start, bounds.end)
			if(cache[url] != undefined) {  me.draw(cache[url]); return; }
			$.ajax({
				url : url
				, dataType : 'jsonp'
				, success : function(json){
					cache[url] = json;
					me.draw(json);
				}
			});
		}
		
	}

	var config = {
		adaptiveHeight : true
		// , animation: true
		, interactive : true
		, drawLegend: true
		, drawHorodator: true
		, width: $(".container").width()
		, decorators : [ new Calendar.decorator.hovered({float:'right'}) ]
	}
	var myCalendar = Calendar(config)
		.timeserie(timeserie)
		.grab(datagrab_closure)
		.renderer(new Calendar.renderer.drillthrough());

	myCalendar.createTiles(2011, "day");
});