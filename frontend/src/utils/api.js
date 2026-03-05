const API_ROOT = import.meta.env.VITE_API_ROOT || 'http://localhost:8080'

async function request(path, opts = {}){
  const res = await fetch(API_ROOT + path, {
    ...opts,
    headers: {
      'Content-Type': 'application/json',
      ...(opts.headers || {})
    }
  })
  if (res.status === 204) return null
  const data = await res.json().catch(() => null)
  return data
}

export default {
  get: (path) => request(path, { method: 'GET' }),
  post: (path, body) => request(path, { method: 'POST', body: JSON.stringify(body) }),
  put: (path, body) => request(path, { method: 'PUT', body: JSON.stringify(body) }),
  delete: (path) => request(path, { method: 'DELETE' }),
  del: (path) => request(path, { method: 'DELETE' })
}
