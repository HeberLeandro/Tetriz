function GUI() {
    var ws = null;
    var col = 10;
    var row = 20;
    var sq = 30;

    var canvas1 = document.getElementById('canvas1');
    var canvas2 = document.getElementById('canvas2');
    var canvasFila1 = document.getElementById('fila1');
    var canvasFila2 = document.getElementById('fila2');
    var ctx1 = canvas1.getContext('2d');
    var ctx2 = canvas2.getContext('2d');
    var ctxFila1 = canvasFila1.getContext('2d');
    var ctxFila2 = canvasFila2.getContext('2d');

    var myBoard, enemyBoard;
    var myPieceQueue, enemyPieceQueue;

    var myCurrentPiece, enemyCurrentPiece;

    var speed = 500;
    var dropStart = Date.now();
    var canMove = true;

    var defaultColor = "black";
    var defaultBorder = 'grey';

    function setMessage(msg) {
        let message = document.getElementById("message");
        message.innerHTML = msg;
    }

    function init() {
        let button = document.querySelector("input[type='button']");
        button.onclick = startGame;
    }
    
    function setButtonText(txt) {
        let button = document.querySelector("input[type='button']");
        button.value = txt;
    }
    
    function startGame() {
        if (ws) {

        } else {
            ws = new WebSocket(`ws://${document.location.host}${document.location.pathname}tetriz`);
            ws.onmessage = readData;
            document.addEventListener("keydown", CONTROL);
        }
    }
    function closeConnection(closeCode) {
        ws.close(closeCode);
        ws = null;
    }

    function readData(evt) {
        let data = JSON.parse(evt.data);
        switch (data.type) {
            case "OPEN":
                setMessage("Waiting for an opponent...");
                myBoard = data.myBoard;
                myPieceQueue = data.myPiecesqueue;

                enemyBoard = data.enemyBoard;
                enemyPieceQueue = data.enemyPiecesqueue;

                myCurrentPiece = myPieceQueue.shift();
                enemyCurrentPiece = enemyPieceQueue.shift();

                drawBoard(myBoard, ctx1);
                drawBoard(enemyBoard, ctx2);
                break;
            case "START":
                setMessage("Opponent found, game started!");
                drawQueue(myPieceQueue[0], ctxFila1);
                drawQueue(enemyPieceQueue[0], ctxFila2);
                drop();
                break;
            case "UPDATE":
                enemyBoard = data.enemyBoard;
                drawBoard(enemyBoard, ctx2);
                break;
            case "REQUEST":
                myPieceQueue = data.myPiecesqueue;
                enemyPieceQueue = data.enemyPiecesqueue;
                enemyBoard = data.enemyBoard;

                drawQueue(myPieceQueue[0], ctxFila1);
                drawQueue(enemyPieceQueue[0], ctxFila2);
                drawBoard(enemyBoard, ctx2);
                break;
            case "ENDGAME":
                console.log(data);
                setMessage("You Win!");
                closeConnection(1000);
                setButtonText("Restart");
                break;
        }
    }

    function sendMessage(board, piece, type, queue) {
        var msg = new FrontMessage(board, piece, type, queue);
        ws.send(JSON.stringify(msg));
    }

    function drawQueue(queuePiece, ctx) {
        for (var i = 0; i < 4; i++) {
            for (var j = 0; j < 4; j++) {
                ctx.fillStyle = defaultColor;
                ctx.fillRect(j * sq, i * sq, sq, sq);
                ctx.strokeStyle = defaultBorder;
                ctx.strokeRect(j * sq, i * sq, sq, sq);
            }
        }

        for (var i = 0; i < queuePiece.activePiece.length; i++) {
            for (var j = 0; j < queuePiece.activePiece.length; j++) {
                ctx.fillStyle = defaultColor;
                if (queuePiece.activePiece[i][j]) {
                    ctx.fillStyle = queuePiece.color;
                }

                ctx.fillRect(j * sq, i * sq, sq, sq);

                if (queuePiece.activePiece[i][j] === 0) {
                    ctx.strokeStyle = defaultBorder;
                }
                ctx.strokeRect(j * sq, i * sq, sq, sq);
            }
        }
    }

    function drawBoard(board, ctx) {
        for (var i = 0; i < row; i++) {
            for (var j = 0; j < col; j++) {
                drawSquare(j, i, board[i][j], ctx);
            }
        }
    }

    function drawSquare(x, y, color, ctx) {
        ctx.fillStyle = color;
        ctx.fillRect(x * sq, y * sq, sq, sq);

        if (color === defaultColor) {
            ctx.strokeStyle = defaultBorder;
        }

        ctx.strokeRect(x * sq, y * sq, sq, sq);
    }

    function drop() {
        const now = Date.now();
        const delta = now - dropStart;

        if (!myCurrentPiece) {
            return;
        }

        if (delta > speed) {
            pieceMoveDown();
            dropStart = Date.now();
        }


        requestAnimationFrame(drop);
    }

    function unDraw() {
        pieceFill(defaultColor, ctx1);
    }

    function draw() {
        pieceFill(myCurrentPiece.color, ctx1);
    }

    function pieceFill(color, ctx) {
        for (let r = 0; r < myCurrentPiece.activePiece.length; r++) {
            for (let c = 0; c < myCurrentPiece.activePiece.length; c++) {
                if (myCurrentPiece.activePiece[r][c]) {
                    drawSquare(myCurrentPiece.x + c, myCurrentPiece.y + r, color, ctx);
                }
            }
        }
    }

    function pieceMoveDown() {
        if (!collision(0, 1, myCurrentPiece.activePiece)) {
            unDraw();
            myCurrentPiece.y++;
            if (myCurrentPiece.y >= 0) {
                sendMessage(myBoard, myCurrentPiece, "UPDATE", myPieceQueue);
            }
            draw();
            return;
        }

        lockPiece();
        sendMessage(myBoard, myCurrentPiece, "REQUEST", myPieceQueue);
        myCurrentPiece = myPieceQueue.shift();
    }

    function collision(x, y, futurePiece) {
        for (let currentRow = 0; currentRow < futurePiece.length; currentRow++) {
            for (let currentCol = 0; currentCol < futurePiece.length; currentCol++) {
                if (!futurePiece[currentRow][currentCol]) {
                    continue;
                }

                let newX = myCurrentPiece.x + currentCol + x;
                let newY = myCurrentPiece.y + currentRow + y;


                if (newX < 0 || newX >= col || newY >= row) {
                    return true;
                }

                if (newY < 0) {
                    continue;
                }
                if (myBoard[newY][newX] !== defaultColor) {
                    return true;
                }
            }
        }
        return false;
    }

    function lockPiece() {
        canMove = false;

        for (let currentRow = 0; currentRow < myCurrentPiece.activePiece.length; currentRow++) {
            for (let currentCol = 0; currentCol < myCurrentPiece.activePiece.length; currentCol++) {
                if (!myCurrentPiece.activePiece[currentRow][currentCol]) {
                    continue;
                }

                if (myCurrentPiece.y + currentRow < 0) {
                    gameOver();
                    break;
                }

                myBoard[myCurrentPiece.y + currentRow][myCurrentPiece.x + currentCol] = myCurrentPiece.color;
                drawBoard(myBoard, ctx1);
            }
        }

        for (let currentRow = 0; currentRow < row; currentRow++) {

            let isRowFull = true;

            for (let currentCol = 0; currentCol < col; currentCol++) {
                const currentSquareColor = myBoard[currentRow][currentCol];
                isRowFull = isRowFull && (currentSquareColor !== defaultColor);
            }

            if (isRowFull) {
                updateRow(currentRow);
            }
        }

        drawBoard(myBoard, ctx1);
        canMove = true;
    }
    function updateRow(row) {
        canMove = false;

        for (let y = row; y > 1; y--) {
            for (let currentCol = 0; currentCol < col; currentCol++) {
                removeRow(y, currentCol);
            }
        }

        for (let currentCol = 0; currentCol < col; currentCol++) {
            myBoard[0][currentCol] = defaultColor;
        }

        if (speed > 100) {
            speed -= 20;
        }

        canMove = true;
    }

    function removeRow(rowToRemove, colToRemove) {
        myBoard[rowToRemove][colToRemove] = myBoard[rowToRemove - 1][colToRemove];
    }

    function pieceRotate() {
        let nextPattern = myCurrentPiece.piecePositions[(myCurrentPiece.pieceRotation + 1) % myCurrentPiece.piecePositions.length];
        let kick = 0;

        if (collision(0, 0, nextPattern)) {
            kick = 1;

            if (myCurrentPiece.x > col / 2) {
                kick = -1;
            }
        }

        if (!collision(kick, 0, nextPattern)) {
            unDraw();
            myCurrentPiece.x += kick;
            myCurrentPiece.pieceRotation = (myCurrentPiece.pieceRotation + 1) % myCurrentPiece.piecePositions.length;
            myCurrentPiece.activePiece = myCurrentPiece.piecePositions[myCurrentPiece.pieceRotation];
            draw();
        }
    }

    function pieceMoveLeft() {
        if (!collision(-1, 0, myCurrentPiece.activePiece)) {
            unDraw();
            myCurrentPiece.x--;
            draw();
        }
    }

    function pieceMoveRight() {
        if (!collision(1, 0, myCurrentPiece.activePiece)) {
            unDraw();
            myCurrentPiece.x++;
            draw();
        }
    }

    function CONTROL(event) {
        if (!canMove) {
            return false;
        }
        const moveFunctions = {
            ArrowLeft() {
                pieceMoveLeft();
                dropStart = Date.now();
            },
            ArrowRight() {
                pieceMoveRight();
                dropStart = Date.now();
            },
            ArrowUp() {
                pieceRotate();
                dropStart = Date.now();
            },
            ArrowDown() {
                pieceMoveDown();
            }
        };

        const movePiece = moveFunctions[event.code];
        movePiece();
    }

    function gameOver() {
        myCurrentPiece = null;
        closeConnection(4000);
        setMessage("Game Over.");
        setButtonText("Restart");
    }
    return {init};
}


onload = function () {
    let gui = new GUI();
    gui.init();
};