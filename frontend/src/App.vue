<template>
  <div id="app">
    <div class="container-fluid">
      <div class="row">
        <div class="col">
          <imageGalleryItem/>
        </div>
        <div class="col">
          <imageGalleryItem/>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <imageGalleryItem/>
        </div>
        <div class="col">
          <imageGalleryItem/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import imageGalleryItem from "./components/ImageGalleryItem.vue";

export default {
  name: "app",
  components: {
    imageGalleryItem
  }
};
</script>

<style>
#app {
  height: 100vh;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: black;
}
.col {
  padding: 2px;
  margin: auto;
}
.row {
  height: 50vh;
}
</style>
