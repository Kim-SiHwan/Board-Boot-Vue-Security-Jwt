import Send from "./common_api"
function requestJoin(payload){
    return Send({
        url:'/api/member/save',
        method:'POST',
        data:payload
    })
}

function requestLogin(payload){
    return Send({
        url:'/api/member/login',
        method:'POST',
        data:payload
    })
}

export default {requestJoin,requestLogin};