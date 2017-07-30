import {mapState, mapMutations} from "vuex"
import Global from "../components/Global.vue"
import store from "./index"

export default {
    getUserInfo(state){
        if(!!mapState.userInfo){
            return mapState.userInfo;
        }
        else{
            store.commit("SET_USER_INFO", Global.getUserInfoFromSessionStorage());
            return mapState.userInfo;
        }
    }

}
