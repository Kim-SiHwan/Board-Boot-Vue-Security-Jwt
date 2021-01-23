import axios from "axios";

function requestJoin(member){
    return axios.post('/api/save',member);
}

function requestLogin(member){
    return axios.post('api/authenticate',member);
}

export default {requestJoin,requestLogin};