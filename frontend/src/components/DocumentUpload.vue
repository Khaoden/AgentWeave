<template>
  <section>
    <h2>Upload document</h2>
    <form @submit.prevent="upload">
      <input type="file" @change="handleFile" />
      <button type="submit" :disabled="!file">Ingest</button>
    </form>
    <p v-if="status">{{ status }}</p>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const emit = defineEmits(['ingested'])
const file = ref(null)
const status = ref('')

const handleFile = (event) => {
  file.value = event.target.files?.[0]
}

const upload = async () => {
  if (!file.value) return
  const form = new FormData()
  form.append('file', file.value)
  status.value = 'Uploading...'
  await axios.post('/api/docs/ingest', form, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
  status.value = 'Ingestion queued for processing.'
  emit('ingested')
}
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

button {
  background: #0f6df2;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
}
</style>
