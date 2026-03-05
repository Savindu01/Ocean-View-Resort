import React from 'react'
import { Outlet, useNavigate, useLocation } from 'react-router-dom'
import { Link as RouterLink } from 'react-router-dom'

// MUI imports
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Container,
  Box
} from '@mui/material'
import HotelIcon from '@mui/icons-material/Hotel'
import HomeIcon from '@mui/icons-material/Home'
import LoginIcon from '@mui/icons-material/Login'
import BookOnlineIcon from '@mui/icons-material/BookOnline'
import HelpIcon from '@mui/icons-material/Help'
import LogoutIcon from '@mui/icons-material/Logout'

export default function App() {
  const navigate = useNavigate()
  const location = useLocation()
  const isLoggedIn = location.pathname === '/reservations'

  const handleLogout = () => {
    navigate('/')
  }

  return (
    <div className="app">
      <AppBar 
        position="static" 
        sx={{ 
          background: 'rgba(0, 0, 0, 0.95)',
          backdropFilter: 'blur(20px)',
          boxShadow: '0 4px 30px rgba(0, 0, 0, 0.5)',
          borderBottom: '1px solid rgba(37, 99, 235, 0.2)'
        }}
      >
        <Toolbar sx={{ py: 1 }}>
          <HotelIcon sx={{ mr: 2, fontSize: 32, color: '#2563eb' }} />
          <Typography 
            variant="h5" 
            component="div" 
            sx={{ 
              flexGrow: 1, 
              fontWeight: 700,
              background: 'linear-gradient(135deg, #1e3a8a 0%, #2563eb 100%)',
              WebkitBackgroundClip: 'text',
              WebkitTextFillColor: 'transparent',
              backgroundClip: 'text',
              textShadow: '0 0 30px rgba(37, 99, 235, 0.3)'
            }}
          >
            Ocean View Resort
          </Typography>
          <Button 
            color="inherit" 
            component={RouterLink} 
            to="/"
            startIcon={<HomeIcon />}
            sx={{ 
              mx: 1,
              fontWeight: 600,
              color: '#e5e7eb',
              '&:hover': {
                background: 'rgba(37, 99, 235, 0.2)',
                transform: 'translateY(-2px)',
                transition: 'all 0.3s ease'
              }
            }}
          >
            Home
          </Button>
          {!isLoggedIn ? (
            <>
              <Button 
                color="inherit" 
                component={RouterLink} 
                to="/login"
                startIcon={<LoginIcon />}
                sx={{ 
                  mx: 1,
                  fontWeight: 600,
                  color: '#e5e7eb',
                  '&:hover': {
                    background: 'rgba(37, 99, 235, 0.2)',
                    transform: 'translateY(-2px)',
                    transition: 'all 0.3s ease'
                  }
                }}
              >
                Login
              </Button>
              <Button 
                color="inherit" 
                component={RouterLink} 
                to="/help"
                startIcon={<HelpIcon />}
                sx={{ 
                  mx: 1,
                  fontWeight: 600,
                  color: '#e5e7eb',
                  '&:hover': {
                    background: 'rgba(37, 99, 235, 0.2)',
                    transform: 'translateY(-2px)',
                    transition: 'all 0.3s ease'
                  }
                }}
              >
                Help
              </Button>
            </>
          ) : (
            <>
              <Button 
                color="inherit" 
                component={RouterLink} 
                to="/reservations"
                startIcon={<BookOnlineIcon />}
                sx={{ 
                  mx: 1,
                  fontWeight: 600,
                  color: '#e5e7eb',
                  '&:hover': {
                    background: 'rgba(37, 99, 235, 0.2)',
                    transform: 'translateY(-2px)',
                    transition: 'all 0.3s ease'
                  }
                }}
              >
                Dashboard
              </Button>
              <Button 
                color="inherit" 
                component={RouterLink} 
                to="/help"
                startIcon={<HelpIcon />}
                sx={{ 
                  mx: 1,
                  fontWeight: 600,
                  color: '#e5e7eb',
                  '&:hover': {
                    background: 'rgba(37, 99, 235, 0.2)',
                    transform: 'translateY(-2px)',
                    transition: 'all 0.3s ease'
                  }
                }}
              >
                Reservations
              </Button>
              <Button 
                color="inherit" 
                component={RouterLink} 
                to="/help"
                startIcon={<HelpIcon />}
                sx={{ 
                  mx: 1,
                  fontWeight: 600,
                  color: '#e5e7eb',
                  '&:hover': {
                    background: 'rgba(37, 99, 235, 0.2)',
                    transform: 'translateY(-2px)',
                    transition: 'all 0.3s ease'
                  }
                }}
              >
                Help
              </Button>
              <Button 
                onClick={handleLogout}
                startIcon={<LogoutIcon />}
                sx={{ 
                  mx: 1,
                  fontWeight: 600,
                  color: '#fff',
                  background: 'linear-gradient(135deg, #ef4444 0%, #dc2626 100%)',
                  px: 3,
                  '&:hover': {
                    background: 'linear-gradient(135deg, #dc2626 0%, #b91c1c 100%)',
                    transform: 'translateY(-2px)',
                    boxShadow: '0 4px 15px rgba(239, 68, 68, 0.4)',
                    transition: 'all 0.3s ease'
                  }
                }}
              >
                Logout
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>
      <Container maxWidth="xl" sx={{ mt: 4, pb: 4 }}>
        <Outlet />
      </Container>
    </div>
  )
}
