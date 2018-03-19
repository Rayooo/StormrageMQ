<template>
    <section id="userInfo">
        <navigation father-component="userInfo"></navigation>


        <canvas id="canvas"></canvas>

        <el-card class="box-card" >
            <div slot="header" class="clearfix" style="text-align: center">
                <span style="font-size: larger">修改密码</span>
            </div>
            <div  class="text item">
                <el-form ref="form" :model="form" label-width="80px">

                    <el-form-item label="用户名">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>

                    <el-form-item label="原密码">
                        <el-input v-model="form.password"></el-input>
                    </el-form-item>

                    <el-form-item label="新密码">
                        <el-input v-model="form.newPassword"></el-input>
                    </el-form-item>

                    <el-form-item label="重复密码">
                        <el-input v-model="form.newPassword2"></el-input>
                    </el-form-item>


                </el-form>


            </div>
        </el-card>


    </section>
</template>

<style>
    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }

    .box-card {
        opacity: 0.9;
        width: 33%;
        margin-left: 33%;
        margin-top: 10%;
    }

    #canvas{
        width: 100%;
        height: 100%;
        position:fixed;
        z-index:-1;
        left: 0;
    }


</style>

<script>
    import Navigation from "../../components/Navigation.vue";
    import Global from "@/components/Global.vue"

    export default {
        components: {Navigation},
        name: "UserInfo",
        data() {
            return {
                form:{}

            }
        },
        mounted() {
            this.ribbon()
        },
        methods:{
            ribbon(){
                document.addEventListener('touchmove', function (e) {
                    e.preventDefault()
                })
                var c = document.getElementById('canvas'),
                    x = c.getContext('2d'),
                    pr = window.devicePixelRatio || 1,
                    w = window.innerWidth,
                    h = window.innerHeight,
                    f = 90,
                    q,
                    m = Math,
                    r = 0,
                    u = m.PI*2,
                    v = m.cos,
                    z = m.random

                c.width = w*pr
                c.height = h*pr
                x.scale(pr, pr)
                x.globalAlpha = 0.6

                function i(){
                    x.clearRect(0,0,w,h)
                    q=[{x:0,y:h*.7+f},{x:0,y:h*.7-f}]
                    while(q[1].x<w+f) d(q[0], q[1])
                }
                function d(i,j){
                    x.beginPath()
                    x.moveTo(i.x, i.y)
                    x.lineTo(j.x, j.y)
                    var k = j.x + (z()*2-0.25)*f,
                        n = y(j.y)
                    x.lineTo(k, n)
                    x.closePath()
                    r-=u/-50
                    x.fillStyle = '#'+(v(r)*127+128<<16 | v(r+u/3)*127+128<<8 | v(r+u/3*2)*127+128).toString(16)
                    x.fill()
                    q[0] = q[1]
                    q[1] = {x:k,y:n}
                }
                function y(p){
                    var t = p + (z()*2-1.1)*f
                    return (t>h||t<0) ? y(p) : t
                }
                // document.onclick = i
                // document.ontouchstart = i
                var times = Math.random()*10;
                while(times-- > 0){
                    i();
                }
            }



        }

    }

</script>
