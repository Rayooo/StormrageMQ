<template>
    <section id="message">
        <navigation father-component="message"></navigation>
        {{msg}}
    </section>
</template>

<script>
    import Global from "@/components/Global.vue"
    import Navigation from "../../components/Navigation.vue";
    import {mapState, mapActions, mapMutations, mapGetters} from "vuex"

    export default {
        components: {Navigation},
        name:"Message",
        data() {
            return {
                msg: ""
            }
        },
        mounted() {
            this.msg = "这是消息列表";
            this.ADD(10);
        },
        methods:{
            ...mapMutations([
                "ADD"
            ]),
        }

    }
</script>
