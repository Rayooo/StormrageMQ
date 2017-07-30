import Global from "../components/Global.vue"
import {initState} from "./state"

export default {

    ["SET_USER_INFO"](state, userInfo) {
        if(!!userInfo){
            Global.setSessionStorage("user", userInfo);
            state.userInfo = userInfo;
            state.token = userInfo.loginToken;
        }
    },
    RESET (s) {
        const initial = initState;
        Object.keys(initial).forEach(key => { s[key] = initial[key] });
    }
}
