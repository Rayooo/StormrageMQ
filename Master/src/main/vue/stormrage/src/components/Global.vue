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

    function post(api, param, callback, errCallback) {
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
