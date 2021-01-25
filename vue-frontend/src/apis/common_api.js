import axios from "axios";

const instance = axios.create();

instance.interceptors.request.use(
    async config=>{
        config.headers={'Authorization' : localStorage.getItem("access_token")}
        return config;
    },
    error => {
        console.log(error);
    }
)
instance.interceptors.response.use(
    response=>{
        return response;
    },
    async error=>{
        if(error.response.status===401){
            console.log("토큰이만료되었음.");
        }else
            console.log("다른에러임.");

        console.log(error.config);
    }

)

export default instance;