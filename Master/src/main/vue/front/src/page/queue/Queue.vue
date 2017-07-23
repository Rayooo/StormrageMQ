<template>
    <section id="queue">
        <navigation father-component="queue"></navigation>

        {{msg}}
    </section>
</template>

<script>
    import Global from "@/components/Global.vue"
    import Navigation from "../../components/Navigation.vue";

    export default {
        components: {Navigation},
        name:"Queue",
        data() {
            return {
                msg: ""
            }
        },
        mounted() {
            this.msg = "这是队列";
        }
    }
</script>
