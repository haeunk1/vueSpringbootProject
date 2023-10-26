<template>
    <Header/>
    <RouterView/>
<!--    <Home/>-->
    <Footer/>
</template>

<script>

import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import store from "@/scripts/store";
import axios from "axios";
import {watch} from "vue";
import {useRoute} from "vue-router/dist/vue-router";
//import Home from "@/pages/Home.vue";

export default {
    name: 'App',
    components: {
        //ome,
        Footer,
        Header
    },
    setup(){
        const check = () =>{
            axios.get("/api/account/check").then(({data})=>{
                console.log(data)
                store.commit("setAccount",data || 0)
            })
        }
        const route = useRoute()

        watch(route,() => {
            check()
        })
        // 기존 : sessionStorage를 이용하여 로그인 체크 -> 경로값이 바뀔 때마다 체크 api로 로그인 값이 있는지 체크
        // const id = sessionStorage.getItem("id")
        // if(id){
        //     store.commit("setAccount",id)
        // }
    }
}
</script>

<style>
.bd-placeholder-img {
    font-size: 1.125rem;
    text-anchor: middle;
    -webkit-user-select: none;
    -moz-user-select: none;
    user-select: none;
}

@media (min-width: 768px) {
    .bd-placeholder-img-lg {
        font-size: 3.5rem;
    }
}

.b-example-divider {
    width: 100%;
    height: 3rem;
    background-color: rgba(0, 0, 0, .1);
    border: solid rgba(0, 0, 0, .15);
    border-width: 1px 0;
    box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
}

.b-example-vr {
    flex-shrink: 0;
    width: 1.5rem;
    height: 100vh;
}

.bi {
    vertical-align: -.125em;
    fill: currentColor;
}

.nav-scroller {
    position: relative;
    z-index: 2;
    height: 2.75rem;
    overflow-y: hidden;
}

.nav-scroller .nav {
    display: flex;
    flex-wrap: nowrap;
    padding-bottom: 1rem;
    margin-top: -1px;
    overflow-x: auto;
    text-align: center;
    white-space: nowrap;
    -webkit-overflow-scrolling: touch;
}


.bd-mode-toggle {
    z-index: 1500;
}

.bd-mode-toggle .dropdown-menu .active .bi {
    display: block !important;
}
</style>
