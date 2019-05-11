<template>
  <div id="imageGalleryItem" class="imageGalleryItem">
    <transition name="fade" mode="out-in">
      <img class="image" :src="img">
    </transition>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "imageGalleryItem",
  data: function() {
    return {
      img: String,
      message: "hello world!"
    };
  },
  created() {
    setInterval(() => {
      axios.get("rest/image/next").then(resp => {
        console.log(resp.data);
        if (resp.data) {
          this.$data.img =
            "data:image/" +
            resp.data.fileEnding +
            ";base64," +
            resp.data.imageData;
          this.$data.message = resp.data.name;
        }
      });
    }, Math.floor(Math.random() * 2000 + 2000));
  },
  methods: {}
};
</script>
<style>
.image {
  max-height: 49vh;
  max-width: 49vw;
  width: auto; /* you can use % */
  height: auto;
  display: block;
  margin: auto;
}
</style>
