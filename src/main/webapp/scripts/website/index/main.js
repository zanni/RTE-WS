/* ************************** */
/* 
 fill displayed select with possible data to displayed
 */
function createColorSelector(data, selected, inversed) {
	var color = d3.select("#color");
	$("#color").empty();
	var color_inversed = d3.select("#color_inversed");
	$("#color_inversed").empty();
	var inversed = {
		noninversed : "Normal",
		inversed : "Inverse"
	};
	var select = color.selectAll("option").data(d3.keys(data));

	select.enter().append("option").text(function(d) {
		return d;
	}).attr("id", function(d) {
		return "color_" + d;
	}).attr("value", function(d) {
		return d;
	});
	select.exit().remove();

	var select_inversed = color_inversed.selectAll("option").data(
			d3.keys(inversed));

	select_inversed.enter().append("option").text(function(d) {
		return inversed[d];
	}).attr("id", function(d) {
		return "color_inversed_" + d;
	}).attr("value", function(d) {
		return d;
	});

	select_inversed.exit().remove();

	$("#color").css("display", "inline");
	$("#color_inversed").css("display", "inline");

	color.select("#color_" + selected).attr("selected", true);
	if (inversed) {
		color_inversed.select("#color_inversed_inversed")
				.attr("selected", true);
	} else {
		color_inversed.select("#color_inversed_noninversed").attr("selected",
				true);
	}
}
/* ************************** */
/*
 * fill weeks select with week data
 */
function createDisplaySelector(data, selected) {
	$("#displays").empty();
	var displays = d3.select("#displays");

	var select = displays.selectAll("option").data(data);

	select.enter().append("option").text(function(d) {
		return d;
	}).attr("id", function(d) {
		return "displays_" + d;
	}).attr("value", function(d) {
		return d;
	});
	select.exit().remove();

	$("#displays").css("display", "inline");

	displays.select("#displays_" + selected).attr("selected", true);
}
/* ************************** */
/*
 * fill weeks select with week data
 */
function createYearSelector(data, selected) {
	$("#years").empty();
	var years = d3.select("#years");

	var select = years.selectAll("option").data(data);

	select.enter().append("option").text(function(d) {
		return d;
	}).attr("id", function(d) {
		return "years_" + d;
	}).attr("value", function(d) {
		return d;
	});
	select.exit().remove();

	$("#years").css("display", "inline");

	years.select("#years_" + selected).attr("selected", true);
}
/* ************************** */
/*
 * fill weeks select with week data
 */
function createWeekSelector(data, selected) {
	$("#weeks").empty();
	var weeks = d3.select("#weeks");

	var select = weeks.selectAll("option").data(data);

	select.enter().append("option").text(function(d) {
		return "Semaine " + d;
	}).attr("id", function(d) {
		return "weeks_" + d;
	}).attr("value", function(d) {
		return d;
	});
	select.exit().remove();

	$("#weeks").css("display", "inline");

	weeks.select("#weeks_" + selected).attr("selected", true);
}
/* ************************** */
/*
 * fill weeks select with week data
 */
function createMonthSelector(selected) {
	
	var year = new Date().getFullYear();
	var data = d3.time.months(new Date(year, 0, 1), new Date(year + 1, 0, 1));
	$("#months").empty();
	var months = d3.select("#months");
	var select = months.selectAll("option").data(data);
	select.enter().append("option").text(d3.time.format("%B")).attr("id",
			function(d, i) {
				return "months_" + i;
			}).attr("value", function(d, i) {
		return i;
	});
	select.exit().remove();

	$("#months").css("display", "inline");

	months.select("#months_" + selected).attr("selected", true);
}
/* ************************** */
/*
 * fill weeks select with week data
 */
function createDaySelector() {
	
	var monday = d3.time.monday(new Date());
	var sunday = new Date();
	sunday.setTime(monday.getTime() + 7 * 24 * 60 * 60 * 1000);
	var data = d3.time.days(monday, sunday);
	$("#days").empty();
	var days = d3.select("#days");

	var select = days.selectAll("option").data(data);

	select.enter().append("option").text(d3.time.format("%A")).attr("id",
			function(d, i) {
				return "days_" + i;
			}).attr("value", function(d, i) {
		return i;
	});
	select.exit().remove();

	$("#days").css("display", "inline");
}
/* ************************** */
/*
 * fill weeks select with week data
 */
function createThemeSelector(data, selected) {
	$("#themes").empty();
	var themes = d3.select("#themes");

	var select = themes.selectAll("option").data(data);

	select.enter().append("option").text(function(d) {
		return d;
	}).attr("id", function(d) {
		return "theme_" + d;
	}).attr("value", function(d) {
		return d;
	});
	select.exit().remove();

	$("#themes").css("display", "inline");

	themes.select("#theme_" + selected).attr("selected", true);
}

$.ready = function() {
	/* ************************** */
	/*
	 * RTE PARSER
	 */
	var rte_parser = Calendar.data.create(RteWS.specialization.time);

	/* ************************** */
	/*
	 * RTE WS INITILIZATION
	 */
	var rte_ws = new RteWS({
		url : "192.168.1.102:8080"
	});

	/*
	 * var request = rte_ws.jsonp("day", "*", function(data){ console.log(data);
	 * }); request(new Date(2013,0,1), new Date(2013,0,2));
	 */

	/* ************************** */
	/*
	 * DATA RETRIEVING CLOSURE DEFINITION
	 * 
	 * Ok I probably loose myself in javascripts closures but let's explain :
	 */
	// I want the closure to be first specialized with data grabbing
	// function in order to calculate bounds when data will be grabbed.
	// This information is related to the current view definition
	var rte_datagrab_closure = function(specializedFunc) {
		// use Calendar.data.bounds to create func
		var computeBounds = Calendar.data.bounds(specializedFunc);

		// Then I want the callback to be specialized with URL
		// attributes (aggregation method and expected fields)
		// Those definition change depending on user event and are not related
		// with data specialization
		return function(agg, fields) {
			console.log(agg, fields);
			// Then I use collected information in order to grabb the data
			// from rte_ws and display it in calendar view
			return function() {

				var me = this;
				// RTE WS JSONP REQUEST FACILITY
				var request = rte_ws.jsonp(agg, fields, function(json) {
					// compute bounds with previously intialized method
					var bounds = computeBounds(json);
					// refresh Buckets and Legend
					me.setBucket(bounds);
					me.createLegend();
					me.setLegend(bounds);
					// draw calendar view
					me.draw(rte_parser(json));
				});
				if (me.renderer.bounds) {
					var bounds = me.renderer.bounds.apply(me, arguments);
					// var bounds = me.renderer.bounds( arguments);
					request(bounds.start, bounds.end);
				}
			};
		};
	};

	/* ************************** */
	/*
	 * COLORBREWER UTIL
	 */
	var getColorBrewerScheme = function(scheme, buckets, inverse) {
		var color = colorbrewer[scheme][buckets];
		if (inverse) {
			return color.reverse();
		}
		return color;
	};
	/* ************************** */
	/*
	 * VIEWS DEFINITION
	 */
	var possible_displays = {
		/* ************************** */
		/*
		 * TAUX CO2 en g/KWh
		 */
		taux_co2 : {
			name : "Co2 produit par kWh consomme (g)",
			rte_closure : Calendar.data.retreiveValueCallbackClosure(
					RteWS.specialization.taux_co2, d3.mean),
			rte_datagrab_closure : rte_datagrab_closure(RteWS.specialization.taux_co2),
			fields : "taux_co2",
			colorScheme : getColorBrewerScheme('Greens', 9, true)
		}
		/* ************************** */
		/*
		 * CONSOMMATION en MWh
		 */
		,
		consommation : {
			name : "Consommation (MWh)",
			rte_closure : Calendar.data.retreiveValueCallbackClosure(
					RteWS.specialization.consommation, d3.mean),
			rte_datagrab_closure : rte_datagrab_closure(RteWS.specialization.consommation),
			fields : "consommation",
			colorScheme : getColorBrewerScheme('Blues', 9, true)
		}
		/* ************************** */
		/*
		 * TAUX_CO2 * CONSOMMATION (tonnes)
		 */
		,
		taux_co2_consommation : {
			name : "CO2 Produit en tonne",
			rte_closure : Calendar.data.retreiveValueCallbackClosure(
					RteWS.specialization.product_co2, d3.sum),
			rte_datagrab_closure : rte_datagrab_closure(RteWS.specialization.product_co2),
			fields : "taux_co2,consommation",
			colorScheme : getColorBrewerScheme('Greens', 9, true)
		}
		/* ************************** */
		/*
		 * PART DE NUCLEAIRE DANS L ENERGIE PRODUITE (%)
		 */
		,
		nucleaire : {
			name : "Pourcentage de nucleaire",
			rte_closure : Calendar.data.retreiveValueCallbackClosure(
					RteWS.specialization.taux_nucleaire, d3.mean),
			rte_datagrab_closure : rte_datagrab_closure(RteWS.specialization.taux_nucleaire),
			fields : "nucleaire,consommation",
			colorScheme : getColorBrewerScheme('PuBu', 9, true)
		}
		/* ************************** */
		/*
		 * PART D ENERGIES RENOUVELABLES DANS L ENERGIE PRODUITE (%)
		 */
		,
		nrj_ren : {
			name : "Pourcentage de energie renouvelable",
			rte_closure : Calendar.data.retreiveValueCallbackClosure(
					RteWS.specialization.taux_ren, d3.mean),
			rte_datagrab_closure : rte_datagrab_closure(RteWS.specialization.taux_ren),
			fields : "consommation,eolien,hydrolique,solaire",
			colorScheme : getColorBrewerScheme('Greens', 9, false)
		}
		/* ************************** */
		/*
		 * PART D ENERGIE FOSSILE (HORS NUCLEAIRE) DANS L ENERGIE PRODUITE (%)
		 */
		,
		fossile : {
			name : "Pourcentage d'energie fossile (hors nucleaire)'",
			rte_closure : Calendar.data.retreiveValueCallbackClosure(
					RteWS.specialization.taux_fossile_no_nucleaire, d3.mean),
			rte_datagrab_closure : rte_datagrab_closure(RteWS.specialization.taux_fossile_no_nucleaire),
			fields : "consommation,nucleaire,charbon,fioul,gaz",
			colorScheme : getColorBrewerScheme('RdYlGn', 9, true)
		}
		/* ************************** */
		/*
		 * ALL
		 */
		,
		all : {
			name : "test",
			rte_closure : Calendar.data.retreiveValueCallbackClosure(
					RteWS.specialization.all, d3.mean),
			rte_datagrab_closure : rte_datagrab_closure(RteWS.specialization.all),
			fields : "*",
			colorScheme : getColorBrewerScheme('RdYlGn', 9, true)
		}
	};

	/* ************************** */
	/*
	 * DEFINE INITIAL VIEW
	 */
	var selectedColorScheme = 'RdYlGn';
	var selectedColorSchemeInverse = true;
	var selectedBuckets = 9;
	var selected_year = 2013;
	var selected_week = 22;
	var selected_month = 5;
	var selected_display = "taux_co2";
	var themes = [ "day", "week", "month", "year" ];
	var displays = [ "taux_co2", "consommation", "taux_co2_consommation",
			"nucleaire", "nrj_ren", "fossile", "all" ];
	var selected_theme = "year";
	var years = [ 2011, 2012, 2013 ];
	var weeks = [];
	for ( var i = 0; i < 53; i++) {
		weeks[i] = i;
	}

	/* ************************** */
	/*
	 * INJECT DATA INTO SELECTORS
	 */
	createColorSelector(colorbrewer, selectedColorScheme,
			selectedColorSchemeInverse);
	createYearSelector(years, selected_year);
	createWeekSelector(weeks, selected_week);
	createMonthSelector(selected_month);
	createDaySelector();
	createThemeSelector(themes, selected_theme);
	createDisplaySelector(displays, selected_display);

	var display = possible_displays[selected_display];
	var agg = "day";

	/* ************************** */
	/*
	 * CALENDAR INITIALIZATION
	 */
	var calendar = new Calendar({
		name : "Co2 produit par kWh consommé (g)",
		renderer : new Calendar.renderer.year()
		// , buckets : display.buckets
		,
		colorScheme : display.colorScheme
		// , colorSchemeInverse : display.colorSchemeInverse
		,
		retreiveDataCallback : display.rte_datagrab_closure,
		retreiveValueCallback : display.rte_closure,
		width : $("#container").width(),
		height : 800
	});

	/* ************************** */
	/*
	 * REFRESH SCREEN UTIL METHOD
	 */
	function refresh(switch_renderer) {
		console.log($("#themes").attr("value"));
		var years = $.map($('#years option:selected'), function(e) {
			return parseInt($(e).val());
		});

		var months = $.map($('#months option:selected'), function(e) {
			return parseInt($(e).val());
		});

		var selected_display = $("#displays").attr("value");
		var display = possible_displays[selected_display];

		switch ($("#themes").attr("value")) {
		case 'day':
			agg = "hour";
			calendar.retreiveDataCallback = possible_displays[selected_display]
					.rte_datagrab_closure(agg, display.fields);
			if (switch_renderer)
				calendar.renderer = new Calendar.renderer.day();

			calendar.createTiles(d3.max(years), $("#weeks").attr("value"), $(
					"#days").attr("value"));
			break;
		case 'week':
			agg = "hour";
			calendar.retreiveDataCallback = possible_displays[selected_display]
					.rte_datagrab_closure(agg, display.fields);
			if (switch_renderer)
				calendar.renderer = new Calendar.renderer.week();
			calendar.createTiles(d3.max(years), $("#weeks").attr("value"));
			break;
		case 'month':
			agg = "day";
			calendar.retreiveDataCallback = possible_displays[selected_display]
					.rte_datagrab_closure(agg, display.fields);
			if (switch_renderer)
				calendar.renderer = new Calendar.renderer.month();
			calendar.createTiles(d3.max(years), months);
			break;
		case 'year':

			agg = "day";
			calendar.retreiveDataCallback = possible_displays[selected_display]
					.rte_datagrab_closure(agg, display.fields);
			if (switch_renderer)
				calendar.renderer = new Calendar.renderer.year();
			calendar.createTiles(years);
			break;
		default:
			agg = "day";
			calendar.retreiveDataCallback = possible_displays[selected_display]
					.rte_datagrab_closure(agg, display.fields);
			if (switch_renderer)
				calendar.renderer = new Calendar.renderer.year();
			calendar.createTiles(years);
			break;
		}

	}

	/* ************************** */
	/*
	 * DISPLAY INITIAL VIEW
	 */

	refresh();

	/* ************************** */
	/*
	 * EVENTS DEFINITION
	 */
	$("#themes").change(function(e) {
		refresh(true);
	});

	$("#color, #color_inversed")
			.change(
					function(e) {
						// calendar.colorScheme = $("#color").attr("value");
						// calendar.colorSchemeInverse =
						// ($("#color_inversed").attr("value") == "inversed")?
						// true : false;
						var inversed = ($("#color_inversed").attr("value") == "inversed") ? true
								: false;
						calendar.colorScheme = getColorBrewerScheme($("#color")
								.attr("value"), 9, inversed);
						// calendar.createLegend();
						refresh();
					});

	$("#weeks, #years, #months, #days").change(function(e) {
		refresh();
	});

	$("#displays")
			.change(
					function(e) {
						var selected_display = $("#displays").attr("value");

						var display = possible_displays[selected_display];

						calendar.name = possible_displays[selected_display].name;

						calendar.colorScheme = display.colorScheme;
						calendar.colorSchemeInverse = display.colorSchemeInverse;

						calendar.retreiveValueCallback = possible_displays[selected_display].rte_closure;
						calendar.retreiveDataCallback = possible_displays[selected_display].rte_datagrab_closure;

						refresh();
					});

};