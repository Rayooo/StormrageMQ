<script>

    import Vue from "vue"
    import router from "../router"
    import {mapState, mapMutations} from "vuex"
    import store from "../store/index"

    function getHost() {
        return "http://localhost:3000";
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

    let unCheckUrl = [];

    function setUnCheckUrl(newUnCheckUrl) {
        unCheckUrl = newUnCheckUrl;
    }

    function post(api, param, callback, errCallback) {
        let addToken = true;
        for (let i = 0; i < unCheckUrl.length; ++i){
            if(unCheckUrl[i] === api){
                addToken = false;
                break;
            }
        }
        if(addToken && unCheckUrl.length > 0){
            Vue.http.headers.common['token'] = mapState.userInfo.loginToken;
            Vue.http.headers.common['userid'] = mapState.userInfo.id;
        }
        Vue.http.post(getHost() + api, param).then(callback,errCallback);
    }

    function logout() {
        sessionStorage.clear();
        router.push({name:"Login"});
        store.commit("RESET");
    }



    export default {
        setUnCheckUrl,
        getHost,
        getSessionStorage,
        setSessionStorage,
        getUserInfoFromSessionStorage,
        post,
        logout
    }


</script>
