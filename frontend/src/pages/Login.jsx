import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../utils/api'

// MUI imports
import {
  Container,
  Box,
  TextField,
  Button,
  Typography,
  Alert,
  Paper,
  InputAdornment,
  IconButton
} from '@mui/material'
import PersonIcon from '@mui/icons-material/Person'
import LockIcon from '@mui/icons-material/Lock'
import Visibility from '@mui/icons-material/Visibility'
import VisibilityOff from '@mui/icons-material/VisibilityOff'
import LoginIcon from '@mui/icons-material/Login'
import WavesIcon from '@mui/icons-material/Waves'

export default function Login() {
  const [username, setUsername] = useState('admin')
  const [password, setPassword] = useState('')
  const [error, setError] = useState(null)
  const [showPassword, setShowPassword] = useState(false)
  const navigate = useNavigate()

  async function submit(e) {
    e.preventDefault()
    setError(null)
    try {
      const res = await api.post('/api/login', { username, password })
      if (res.success) {
        navigate('/reservations')
      } else {
        setError('Invalid credentials')
      }
    } catch (err) {
      setError('Error connecting to server')
    }
  }

  return (
    <Container maxWidth="sm">
      <Box
        className="fade-in"
        sx={{
          mt: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center'
        }}
      >
        <Paper
          elevation={0}
          sx={{
            p: 5,
            width: '100%',
            background: 'rgba(10, 25, 41, 0.8)',
            backdropFilter: 'blur(20px)',
            borderRadius: 4,
            boxShadow: '0 8px 32px 0 rgba(0, 0, 0, 0.5)',
            border: '1px solid rgba(37, 99, 235, 0.2)'
          }}
        >
          <Box sx={{ textAlign: 'center', mb: 4 }}>
            <WavesIcon 
              sx={{ 
                fontSize: 60, 
                color: '#2563eb',
                mb: 2,
                filter: 'drop-shadow(0 0 20px rgba(37, 99, 235, 0.5))',
                animation: 'pulse 2s ease-in-out infinite'
              }} 
            />
            <Typography 
              component="h1" 
              variant="h4" 
              sx={{ 
                fontWeight: 700,
                background: 'linear-gradient(135deg, #1e3a8a 0%, #2563eb 100%)',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent',
                backgroundClip: 'text',
                mb: 1
              }}
            >
              Welcome Back
            </Typography>
            <Typography variant="body2" sx={{ color: '#9ca3af' }}>
              Sign in to manage your resort reservations
            </Typography>
          </Box>

          <Box component="form" onSubmit={submit} sx={{ width: '100%' }}>
            <TextField
              label="Username"
              fullWidth
              margin="normal"
              value={username}
              onChange={e => setUsername(e.target.value)}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <PersonIcon sx={{ color: '#2563eb' }} />
                  </InputAdornment>
                ),
              }}
              sx={{
                '& .MuiOutlinedInput-root': {
                  background: 'rgba(0, 0, 0, 0.5)',
                  color: '#e5e7eb',
                  '& fieldset': {
                    borderColor: 'rgba(37, 99, 235, 0.3)',
                  },
                  '&:hover fieldset': {
                    borderColor: '#2563eb',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#2563eb',
                  },
                },
                '& .MuiInputLabel-root': {
                  color: '#9ca3af',
                },
                '& .MuiInputLabel-root.Mui-focused': {
                  color: '#2563eb',
                },
              }}
            />
            <TextField
              label="Password"
              type={showPassword ? 'text' : 'password'}
              fullWidth
              margin="normal"
              value={password}
              onChange={e => setPassword(e.target.value)}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <LockIcon sx={{ color: '#2563eb' }} />
                  </InputAdornment>
                ),
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      onClick={() => setShowPassword(!showPassword)}
                      edge="end"
                      sx={{ color: '#9ca3af' }}
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
              sx={{
                '& .MuiOutlinedInput-root': {
                  background: 'rgba(0, 0, 0, 0.5)',
                  color: '#e5e7eb',
                  '& fieldset': {
                    borderColor: 'rgba(37, 99, 235, 0.3)',
                  },
                  '&:hover fieldset': {
                    borderColor: '#2563eb',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#2563eb',
                  },
                },
                '& .MuiInputLabel-root': {
                  color: '#9ca3af',
                },
                '& .MuiInputLabel-root.Mui-focused': {
                  color: '#2563eb',
                },
              }}
            />
            {error && (
              <Alert 
                severity="error" 
                sx={{ 
                  mt: 2,
                  borderRadius: 2,
                  animation: 'slideIn 0.3s ease-out',
                  background: 'rgba(239, 68, 68, 0.1)',
                  border: '1px solid rgba(239, 68, 68, 0.3)',
                  color: '#fca5a5'
                }}
              >
                {error}
              </Alert>
            )}
            <Button
              type="submit"
              variant="contained"
              fullWidth
              startIcon={<LoginIcon />}
              sx={{ 
                mt: 3,
                py: 1.5,
                fontSize: '1rem',
                fontWeight: 600,
                borderRadius: 2,
                background: 'linear-gradient(135deg, #1e3a8a 0%, #2563eb 100%)',
                boxShadow: '0 4px 15px rgba(37, 99, 235, 0.4)',
                '&:hover': {
                  background: 'linear-gradient(135deg, #2563eb 0%, #1e3a8a 100%)',
                  transform: 'translateY(-2px)',
                  boxShadow: '0 6px 20px rgba(37, 99, 235, 0.6)',
                  transition: 'all 0.3s ease'
                }
              }}
            >
              Sign In
            </Button>
          </Box>

          <Box sx={{ mt: 3, textAlign: 'center' }}>
            <Typography variant="caption" sx={{ color: '#6b7280' }}>
              Default credentials: admin / admin123
            </Typography>
          </Box>
        </Paper>
      </Box>
    </Container>
  )
}
