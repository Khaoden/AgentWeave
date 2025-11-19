<template>
  <main class="layout">
    <header>
      <h1>AgentWeave</h1>
      <p>Upload documents, watch agent progress, and view generated reports.</p>
    </header>
    <section class="grid">
      <DocumentUpload @ingested="handleIngested" />
      <TaskProgress :tasks="tasks" />
      <ReportView :taskResult="selectedResult" />
    </section>
  </main>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'
import DocumentUpload from './components/DocumentUpload.vue'
import TaskProgress from './components/TaskProgress.vue'
import ReportView from './components/ReportView.vue'

const tasks = ref([])
const selectedResult = ref(null)

const fetchTasks = async () => {
  const response = await axios.get('/api/tasks')
  tasks.value = response.data
}

const handleIngested = async () => {
  await fetchTasks()
}

const fetchResult = async (taskId) => {
  const response = await axios.get(`/api/tasks/${taskId}/result`).catch(() => null)
  selectedResult.value = response?.data
}

onMounted(async () => {
  await fetchTasks()
  if (tasks.value.length) {
    await fetchResult(tasks.value[0].id)
  }
})
</script>

<style scoped>
.layout {
  font-family: Inter, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
  padding: 2rem;
  color: #1f2933;
}

header {
  margin-bottom: 1.5rem;
}

a {
  color: #0f6df2;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1rem;
}

section :deep(section) {
  background: #f8fafc;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 1rem;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.03);
}
</style>
