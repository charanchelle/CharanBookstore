<script setup lang="ts">

const baseUrl = `${import.meta.env.BASE_URL}/index.html`;</script>

<style scoped>
footer {
    background: var(--secondary-background-color);
    display: flex;
    justify-content: space-between;
    padding: 1em;
    gap: 1em;
}

.links,
.social-media-icons {
    display: flex;
    flex-direction: column;
}

.social-media-icons {
    text-align: center;
    gap: 0.25em;
}
</style>

<template>
    <footer class="container" style="height: 60px;">
        <section class="copyright"><br>
            <span class="fa-layers fa-fw">
            <i style = "color: white; position:relative; top:-15px;  " class="fa-regular fa-circle fa-1x"></i>
            <span style = "size: 10px; position:relative; left:-12px; top:-16px;" class="fa-layers-text fa-inverse">c</span>
            <span style = "size: 10px; position:relative; top:-15px; left:-6px;" class="fa-layers-text fa-inverse"> 2023 Charan Bookstore</span>
          </span>
        </section>
        <section class="links">
            <a style = "text-decoration: none; color: white; size: 10px; position:relative; left:-116px; top:4px;" href="/CharanBookstoreTransact/index.html">about</a>
            <p style = "color: white; size: 10px; position:relative; left:-70px; top:-18px;">|</p>
            <a style = "text-decoration: none; color: white; size: 10px; position:relative; left:-61px; top:-39px;" href="/CharanBookstoreTransact/index.html">contact</a>
            <p style = "color: white; size: 10px; position:relative; left:-2px; top:-61px;">|</p>
            <a style = "text-decoration: none; color: white; size: 10px; position:relative; left:6px; top:-82.2px;" href="/CharanBookstoreTransact/index.html">directions</a>
        </section>
        <section class="social-media-icons">
            <i style="position:relative; left:-50px; top:-2px; cursor: pointer;" class="fa-brands fa-square-facebook fa-2x"></i>
            <i style="position:relative; left:-9px; top:-38px; cursor: pointer;" class="fa-brands fa-square-twitter fa-2x"></i>
        </section>
    </footer>
</template>
