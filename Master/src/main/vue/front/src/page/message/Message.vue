<template>
    <section id="message">
        <navigation father-component="message"></navigation>

        这是消息

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

            }
        },
        mounted() {


        },
        methods:{


        }

    }
</script>
