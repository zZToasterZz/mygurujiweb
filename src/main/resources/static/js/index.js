function myFun(e){
	var percent = Math.floor(Math.random() * 101);
	var canvas = document.getElementById(e);
	var ctx = canvas.getContext('2d');
	canvas.width = 120;
	canvas.height = 80;
	
	var firstProgressBar = new RadialBar(ctx, {
		x: 60,
		y: 40,
		angle: 250,
		radius: 20,
		lineWidth: 5,
		lineFill: '#CCB566',
		backLineFill: '#FB6929',
		bgFill: '#F8FF8E',
		isShowInfoText: true,
		infoStyle: '12px Arial'
	});
	
	loop(percent, canvas, ctx, firstProgressBar);
	
	function loop(disPer, canvas, ctx, firstProgressBar) {
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		
		firstProgressBar.set(disPer);
		firstProgressBar.update();
	}
}