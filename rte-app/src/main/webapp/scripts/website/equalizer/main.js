var fpsDecorator = function(spec) {
	var me = this;
	me.id = "fps_decorator";
	// theming
	if (!spec)
		spec = {};
	me.float = spec.float || 'left';
	me.position = spec.position || 'top';
	var drawn = false;
	me.draw = function() {
		var calendar = this;
		if (!drawn)
			drawn = true;
		else
			return;
		me.decorator = calendar.decoratorEnter(me.id, me.float, me.position);
		me.node = calendar.decoratorTextEnter(me.decorator).text("0 fps");
	};
	me.refresh = function(value) {
		if (me.node)
			me.node.text(value);
	};
};

var analyserRenderer = function(spec) {
	var me = this;
	if (!spec)
		spec = {};
	me.tiles_width = spec.tiles_width || 10;
	me.tiles_height = spec.tiles_height || 6;
	me.cell_size = spec.cell_size || 36;
	me.space_between_tiles = spec.space_between_tiles || 2;

	me.tiles_data = [];

	var drawn = false;
	for ( var i = 0; i < me.tiles_width; i++) {
		for ( var j = 0; j < me.tiles_height; j++) {
			me.tiles_data.push({
				x : i,
				y : j
			});
		}
	}
	me.fps = new fpsDecorator({
		position : 'bottom'
	});

	var time = new Date().getTime();
	me.draw = function(data) {

		var calendar = this;
		var colorize = function(d) {
			var val = calendar.retreiveValueCallback(data, d);
			if (!val)
				return "#ffcc00";
			val = val / 256;
			var ratio = d.y / me.tiles_height;
			if (val > ratio)
				return "#605838";
			else
				return "#ffcc00";
		};
		for ( var i = 0; i < me.tiles_data.length; i++) {
			me.tiles_data[i].color = colorize(me.tiles_data[i]);
		}

		var now = new Date().getTime();
		me.fps.refresh(Math.round(3600 / (now - time), 2) + " fps");
		time = now;

		// ref on DOM
		var svg = calendar.svg;

		var tiles = svg.selectAll("." + calendar.tileClass).data(me.tiles_data,
				function(d) {
					return d.x + "-" + d.y + "-" + d.color;
				});

		var margin = 0;
		
		

		// tiles enter
		calendar.tilesEnter(tiles).attr("x", function(d) {
			return d.x * (me.cell_size + me.space_between_tiles);
		}).attr("y", function(d) {
			return margin + d.y * (me.cell_size + me.space_between_tiles);
		}).attr("width", me.cell_size + "px").attr("height",
				me.cell_size + "px").attr("fill", function(d) {
			return d.color;
		}).attr("fill-opacity", 1);

		// calendar.tilesUpdate(tiles)
		tiles.transition().duration(function(d) {
			return 0;
		}).attr("fill", function(d) {
			return d.color;
		});

		if (!drawn) {
			drawn = true;
			me.fps.draw.apply(calendar);
		}
		
		return {
			width : me.tiles_width
					* (me.cell_size + me.space_between_tiles),
			height : me.tiles_height
					* (me.cell_size + me.space_between_tiles)
		};

		
		
	};
};

// place the rAF *before* the render() to assure as close to
// 60fps with the setTimeout fallback.

tiles_width = 40;
tiles_height = 12;

$.ready = function() {
	// init the library
	var webaudio = new WebAudio();
	// create a sound
	var sound = webaudio.createSound();
	// load sound.wav and play it
	sound.load('resources/mokup/techno.mp3', function(sound) {
		sound.play();
	});
	var calendar = new Calendar({
		name : "Equalizer",
		renderer : new analyserRenderer({
			tiles_width : tiles_width,
			tiles_height : tiles_height
		}),
		retreiveDataCallback : function() {
			var calendar = this;
			calendar.draw(sound.makeHistogram(tiles_width));
		},
		retreiveValueCallback : function(data, d) {
			return data[d.x];
		},
		width : $("#container").width(),
		height : 400,
		duration : 0,
		drawLegend : false,
		drawHorodator : false,
		drawHovered : false
	});

	calendar.createTiles();

	var time = new Date().getTime();

	var context = webaudio.context();

	// shim layer with setTimeout fallback
	window.requestAnimFrame = (function() {
		return window.requestAnimationFrame
				|| window.webkitRequestAnimationFrame
				|| window.mozRequestAnimationFrame || function(callback) {
					window.setTimeout(callback, 1000 / 60);
				};
	})();

	var context = webaudio.context();
	(function animloop() {
		requestAnimFrame(animloop);
		if (sound.isPlayable() === false)
			return;
		if (context.activeSourceCount > 0) {
			calendar.createTiles();
		}
	})();

	$.ajax({
		url : "resources/scripts/website/equalizer/main.js",
		success : function(data) {
			// code highlight
			$('pre code').text(data);
			$('pre code').each(function(i, e) {
				hljs.highlightBlock(e);
			});
		}
	});
};