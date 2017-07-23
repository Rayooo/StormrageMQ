export default {
    ["ADD"](state, num) {
        state.aNumber += num;
    },

    ["INITIALIZE_DATA"](state) {
        state.aNumber = 10;
    }
}
