import Send from "@/apis/common_api";

function getAllBoardsByKeyword(payload){

    return Send({
        url:'/api/board',
        method:'GET',
        params:{
            keyword:payload
        }
    })
}

function getBests(){
    return Send({
        url:'/api/board/best',
        method:'GET'
    });
}
function getBoard(payload){
    return Send({
        url:'/api/board/'+payload,
        method:'GET'
    })
}

function createBoard(board){
    return Send({
        url:'/api/board',
        method:'POST',
        data:board
    })
}

function deleteBoard(boardId){
    return Send({
        url:'/api/board/'+boardId,
        method:'DELETE'
    })
}

function updateBoard(payload){
    return Send({
        url:'/api/board',
        method:'PATCH',
        data:payload
    })

}
export default {getBoard,getBests,getAllBoardsByKeyword,createBoard,deleteBoard,updateBoard}
