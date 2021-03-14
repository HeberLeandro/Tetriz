function GUI() {
    var ws = null;
    var col = 10;
    var row = 20;
    var sq = 30;

    var canvas1 = document.getElementById('canvas1');
    var canvas2 = document.getElementById('canvas2');
    var ctx1 = canvas1.getContext('2d');
    var ctx2 = canvas2.getContext('2d');

    var board;
    var speed = 500;
    var dropStart = Date.now();

    var defaultColor = "black";
    var defaultBorder = 'grey';
    
    var pieces = [];

    var piece = [
                    [0, 0, 0, 0],
                    [1, 1, 1, 1],
                    [0, 0, 0, 0],
                    [0, 0, 0, 0]
                ];
    var pieceX = 3;
    var pieceY = -2;

    function setMessage(msg) {
        let message = document.getElementById("message");
        message.innerHTML = msg;
    }

    function init() {
        let button = document.querySelector("input[type='button']");
        //drawBoard();
        button.onclick = startGame;
    }

    function startGame() {
        if (ws) {
            // closeConnection(4000);
            console.log("fechando");
        } else {
            ws = new WebSocket(`ws://${document.location.host}${document.location.pathname}tetriz`);
            ws.onmessage = readData;
            // setButtonText(false);

        }
    }

    function readData(evt) {
        let data = JSON.parse(evt.data);
        console.log(data);
        switch (data.type) {
            case "OPEN":
                setMessage("Conection open");
                board = data.board;
                pieces = data.pieces;
                break;
            case "START":
                drawBoard(); 
                break;
            case "MESSAGE":
//                printBoard(data.board);
//                setMessage(data.turn === player ? "Your turn." : "Opponent's turn.");
                break;
            case "ENDGAME":
//                printBoard(data.board);
//                closeConnection(1000, data.winner);
                break;
        }
    }

    function drawBoard() {
        for (var i = 0; i < row; i++) {
            for (var j = 0; j < col; j++) {
                drawSquare(j, i, board[i][j]);
            }
        }
        piece = pieces[0].activePiece;
        drop();
        piece = pieces[1].activePiece;
        drop();
    }

    function drawSquare(x, y, color) {
        ctx1.fillStyle = color;
        ctx1.fillRect(x*sq, y*sq, sq, sq);
        console.log(color);
        
       if (color === defaultColor) {
            ctx1.strokeStyle = defaultBorder;
        } 

        ctx1.strokeRect(x*sq, y*sq, sq, sq);
    }

    function drop() {
        const now = Date.now();
        const delta = now - dropStart;

        if (delta > speed) {
            pieceMoveDown();
            dropStart = Date.now();
        } else if (pieceY >= 18){
            pieceY = -2;
            return;
        }
            

        requestAnimationFrame(drop);
    }
    
    function unDraw(){
        pieceFill(defaultColor);
    }
    
    function draw(){
        pieceFill("red");
    }
    
    function pieceFill(color){
        for (let r = 0; r < piece.length; r++) {
            for (let c = 0; c < piece.length; c++) { 
                if (piece[r][c]) {
                    drawSquare(pieceX + c, pieceY+r, color);
                }
            }
        }
    }
    
    function pieceMoveDown() {
        unDraw();
        pieceY++;
        draw();
        console.log(pieceY);
    }
   


    return {init};
}


onload = function () {
    let gui = new GUI();
    gui.init();
};