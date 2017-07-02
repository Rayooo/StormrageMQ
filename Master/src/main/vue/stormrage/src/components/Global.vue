<script>

    import {Vue} from "../main"

    const globalVariable = {};

    const userInfo =  getUserInfoFromSessionStorage();

    function getHost() {
        return "http://localhost:3000/";
    }

    function getSessionStorage(name) {
        return JSON.parse(sessionStorage.getItem(name));
    }

    function setSessionStorage(name,object) {
        sessionStorage.setItem(name, JSON.stringify(object));
    }

    function getUserInfoFromSessionStorage() {
        return getSessionStorage("user")
    }

    const unCheckUrl = [
        "",
        "userAccount/login",
        "userAccount/register"
    ];

    function post(api, param, callback, errCallback) {
        let addToken = true;
        for (let i = 0; i < unCheckUrl.length; ++i){
            if(unCheckUrl[i] === api){
                addToken = false;
            }
        }
        if(addToken){
            Vue.http.headers.common['token'] = userInfo.loginToken;
            Vue.http.headers.common['userid'] = userInfo.id;
        }
        Vue.http.post(getHost() + api, param).then(callback,errCallback);
    }

    export default {
        globalVariable,
        userInfo,
        getHost,
        getSessionStorage,
        setSessionStorage,
        getUserInfoFromSessionStorage,
        post
    }


</script>
