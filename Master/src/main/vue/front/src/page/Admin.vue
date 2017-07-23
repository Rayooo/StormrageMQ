<template>
    <section id="admin">
        <navigation></navigation>
        {{msg}}
    </section>
</template>

<script>
    import Global from "../components/Global.vue"
    import Navigation from "../components/Navigation.vue";
    export default {
        components: {Navigation},
        name:"Admin",
        data() {
            return {
                msg: ""
            }
        },
        mounted() {
            this.msg = Global.userInfo;
        }
    }
</script>
