/**
 * 
 */

var mCavas;
var mCanvasContext;
var isPainting = false;// 드로잉 감지 변수


var mDrawMemoary = new Array();// 전체 드로잉 정보 저장
var mDrawObject ; // 드로잉 정보 

var mBucketColor = colorPurple;
var ACTION_TOOL_BRUSH = 1;
var ACTION_TOOL_BUCKETFILL = 2;
var ACTION_TOOL_ERASER = 3;

var ACTION_TOOL_MODE = ACTION_TOOL_BRUSH;


var imageData ; 
var imageData;
var mCanvasWidth , mCanvasHeight;

var colorPurple = [
		203,
		53,
		148,
		255
	],
	colorGreen = [
		101,
		155,
		65,
		255
	],
	colorYellow = [
		255,
		207,
		51,
		255
	],
	colorBrown = [
		152,
		105,
		40,
		255
	];

$(document).ready(function(){
	canvasInit();
})
/**
 * Image to File
 * @param canvas
 */
function convertCanvasToImage(canvas) {
	var image = new Image();
	image.src = canvas.toDataURL("image/png");
	var data = atob( dataURL.substring( "data:image/png;base64,".length ) ),
    asArray = new Uint8Array(data.length);
	return image;
}
/**
 * 캔버스 최초 초기화
 */
function canvasInit(){
	 mCavas = document.getElementById("myCanvas");
	 mCanvasContext = mCavas.getContext('2d');
	 mCanvasWidth = mCanvasContext.canvas.width;
	 mCanvasHeight = mCanvasContext.canvas.height;
	 
	 toolStrokeStyle("#aabbaa", 5);
	 painterMouseInit();
	 painterTouchInit();
	 toolInit();
}
/**
 * 페인터 마우스 액션 정의
 */
function painterMouseInit(){
	$('#myCanvas').mousedown(function(e){
		if(ACTION_TOOL_MODE == ACTION_TOOL_BUCKETFILL){
			isPainting = false;
			var mouseX = e.pageX - this.offsetLeft;
			var mouseY = e.pageY - this.offsetTop;
			toolBucketFill(mouseX , mouseY);
		}else{
			isPainting = true;
			mDrawObject = new Array();
		}
		
	});
	$('#myCanvas').mousemove(function(e){
		if(isPainting){
			var mouseX = e.pageX - this.offsetLeft;
			var mouseY = e.pageY - this.offsetTop;
			mDrawObject.push({'x': mouseX , 'y':mouseY});
			DrawObject();
		}
	});
	$('#myCanvas').mouseup(function(e){
		isPainting = false;
		mDrawObject = null;
	});
	$('#myCanvas').mouseleave(function(e){
		isPainting = false;
		mDrawObject = null;
	});
	
	
	
}
function painterTouchInit(){
	$('#myCanvas').bind('touchstart',function(event){ 
		console.log("touchstart");
		if(ACTION_TOOL_MODE == ACTION_TOOL_BUCKETFILL){
			isPainting = false;
			  event.preventDefault(); 
			 var e = event.originalEvent; 
			var mouseX = e.targetTouches[0].pageX - this.offsetLeft;
			var mouseY = e.targetTouches[0].pageY - this.offsetTop;
			toolBucketFill(mouseX , mouseY);
		}else{
			isPainting = true;
			mDrawObject = new Array();
		}
		
	});
	$('#myCanvas').bind('touchmove', function(event){ 
		console.log("touchmove");
		if(isPainting){
			  event.preventDefault(); 
			 var e = event.originalEvent; 
			var mouseX = e.targetTouches[0].pageX - this.offsetLeft;
			var mouseY = e.targetTouches[0].pageY - this.offsetTop;
			mDrawObject.push({'x': mouseX , 'y':mouseY});
			DrawObject();
		}
	});
	$('#myCanvas').bind('touchend', function(e){ 
		console.log("touchend");
		isPainting = false;
		mDrawObject = null;
	});
	
	
	
}


/**
 * 도구 초기화
 */
function toolInit(){
	$('#clear').click(function(){
		toolClear();
	});
	$('#eraser').click(function(){
		toolEraser()
	});
	$('#fill_red').click(function(){
		toolBucketColor(colorPurple);
	});
	$('#fill_green').click(function(){
		toolBucketColor(colorGreen);
	});
	$('#fill_blue').click(function(){
		toolBucketColor(colorBrown);
	});
	$('#line_red').click(function(){
		toolStrokeStyle("#FF0000", 10);
	});
	$('#line_green').click(function(){
		toolStrokeStyle("#00ff00", 10);
	});
	$('#line_blue').click(function(){
		toolStrokeStyle("#0000ff", 10);
	});
	$('#save').click(function(){
		saveCanvasToFile();
	});
}
/**
 * 그림정보 저장
 */
function DrawSave(){
	
}
/**
 * mDrawMemoary 의 마지막 정보를 그린다 
 */
function DrawObject(){
	
	if(mDrawObject == undefined || mDrawObject == null){
		console.log('Null Draw');
		return ;
	}	

	var temp = null;
	mCanvasContext.beginPath();
	for(var index  = 0 ; index < mDrawObject.length ; index ++ ){
		if(temp != null){
			mCanvasContext.moveTo(temp.x , temp.y);
			mCanvasContext.lineTo(mDrawObject[index].x , mDrawObject[index].y);
		}
		temp = mDrawObject[index];
	}
	mCanvasContext.closePath();
	mCanvasContext.stroke();
	
	
}

/**
 * 화면 흰색으로 채워서 지우기  
 */
function toolClear(){
	console.log('toolClear()');
	mCanvasContext.clearRect(0, 0, mCanvasContext.canvas.width, mCanvasContext.canvas.height); // Clears the canvas
}
/**
 * 지우개 선택
 */
function toolEraser(){
	ACTION_TOOL_MODE = ACTION_TOOL_ERASER;
	 toolStrokeStyle("#FFFFFF", 10);
}
/**
 * 선스타일
 * @param strokeStyle
 * @param lineWidth
 */
function toolStrokeStyle(strokeStyle , lineWidth){
	ACTION_TOOL_MODE = ACTION_TOOL_BRUSH;
	mCanvasContext.strokeStyle = strokeStyle;
	mCanvasContext.lineJoin = 'round';
	mCanvasContext.lineWidth = lineWidth;
}

/**
 * 페인트통 색상 선택
 */
function toolBucketColor(color){
	ACTION_TOOL_MODE = ACTION_TOOL_BUCKETFILL;
	mBucketColor = color;
	
}
/**
 * 패인트통 만들기 
 */
function toolBucketFill(startX , startY){
	ACTION_TOOL_MODE = ACTION_TOOL_BUCKETFILL;
	console.log('mCanvasWidth[' + mCanvasWidth+']mCanvasHeight[' + mCanvasHeight);
	// 켄버스 이미지 가져오기 
	var mapData = mCanvasContext.getImageData(0, 0, mCanvasWidth, mCanvasHeight);
	// 배경 칠하기 
	toolClear();

	
	floodFill(mapData , mBucketColor, 100, startX , startY);

	// 칠하기
	mCanvasContext.putImageData(mapData, 0, 0);
}

/**
 * https://github.com/katspaugh/canvas-bucket
 * 실제 칠하는 알고리즘
 * @param bitmap
 * @param replacementColor
 * @param tolerance
 * @param x
 * @param y
 */
function floodFill(bitmap, replacementColor, tolerance, x, y) {
    var RGBA = 4;
    var start = getPixelArrayIndex(x || 0, y || 0);
    var queue = [];

    /**
     * http://en.wikipedia.org/wiki/Flood_fill
     */
    (function (node, targetColor, replColor, toler) {
        queue.push(node);

        while (queue.length) {
            node = queue.pop();

            if (colorEquals(node, targetColor, toler)) {
                setColor(node, replColor);

                queue.push(
                    getNode('west' , node),
                    getNode('east' , node),
                    getNode('north', node),
                    getNode('south', node)
                );
            }
        }
    }(
        start,
        Array.prototype.slice.call(bitmap.data, start, start + RGBA),
        replacementColor || [ 0, 0, 0, 0 ],
        tolerance || 10
    ));

    function colorEquals(node, color, tolerance) {
        if (node < 0 || node + RGBA - 1 > bitmap.data.length) {
            return false;
        }

        var diff = 0;
        for (var i = 0; i < RGBA; i += 1) {
            diff += Math.abs(bitmap.data[node + i] - color[i]);
        }
        return diff <= tolerance;
    }

    function setColor(node, color) {
        for (var i = 0; i < RGBA; i += 1) {
            bitmap.data[node + i] = color[i];
        }
    }

    function getNode(direction, node) {
        var n = 0;
        switch (direction) {
            case 'west':
                n = 1;
                break;
            case 'east':
                n = -1;
                break;
            case 'north':
                n = -bitmap.width;
                break;
            case 'south':
                n = bitmap.width;
                break;
        }

        return node + n * RGBA;
    }

    function getPixelArrayIndex(x, y) {
        return (y * bitmap.width + x) * RGBA;
    }
}
