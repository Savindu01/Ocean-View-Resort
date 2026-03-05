import React from 'react'
import { useNavigate } from 'react-router-dom'
import {
  Container, Box, Typography, Button, Grid, Card, CardContent, Paper
} from '@mui/material'
import HotelIcon from '@mui/icons-material/Hotel'
import BookOnlineIcon from '@mui/icons-material/BookOnline'
import ReceiptIcon from '@mui/icons-material/Receipt'
import PeopleIcon from '@mui/icons-material/People'
import StarIcon from '@mui/icons-material/Star'
import WifiIcon from '@mui/icons-material/Wifi'
import RestaurantIcon from '@mui/icons-material/Restaurant'
import PoolIcon from '@mui/icons-material/Pool'
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter'
import LocalParkingIcon from '@mui/icons-material/LocalParking'
import ArrowForwardIcon from '@mui/icons-material/ArrowForward'

export default function Home() {
  const navigate = useNavigate()

  const features = [
    { icon: <WifiIcon />, title: 'Free WiFi', desc: 'High-speed internet throughout' },
    { icon: <RestaurantIcon />, title: 'Restaurant', desc: 'Fine dining experience' },
    { icon: <PoolIcon />, title: 'Swimming Pool', desc: 'Outdoor pool with ocean view' },
    { icon: <FitnessCenterIcon />, title: 'Fitness Center', desc: '24/7 gym access' },
    { icon: <LocalParkingIcon />, title: 'Free Parking', desc: 'Secure parking available' },
    { icon: <PeopleIcon />, title: '24/7 Service', desc: 'Round-the-clock assistance' }
  ]

  const rooms = [
    { type: 'Single Room', price: '5,000', desc: 'Perfect for solo travelers', color: '#3b82f6' },
    { type: 'Double Room', price: '8,000', desc: 'Ideal for couples', color: '#10b981' },
    { type: 'Family Room', price: '12,000', desc: 'Spacious for families', color: '#f59e0b' },
    { type: 'Suite', price: '20,000', desc: 'Luxury experience', color: '#1e3a8a' }
  ]

  return (
    <Box sx={{ minHeight: '100vh' }}>
      {/* Hero Section */}
      <Box sx={{ 
        textAlign: 'center', 
        py: 8,
        background: 'linear-gradient(135deg, rgba(0, 0, 0, 0.8) 0%, rgba(10, 25, 41, 0.8) 100%)',
        borderRadius: 4,
        mb: 6
      }}>
        <HotelIcon sx={{ 
          fontSize: 80, 
          color: '#2563eb', 
          mb: 3,
          filter: 'drop-shadow(0 0 30px rgba(37, 99, 235, 0.6))'
        }} />
        <Typography variant="h2" sx={{ 
          fontWeight: 700, 
          color: '#e5e7eb', 
          mb: 2,
          textShadow: '0 0 30px rgba(37, 99, 235, 0.5)'
        }}>
          Ocean View Resort
        </Typography>
        <Typography variant="h5" sx={{ color: '#9ca3af', mb: 4, maxWidth: 800, mx: 'auto' }}>
          Experience luxury and comfort by the ocean. Your perfect getaway awaits.
        </Typography>
        <Box sx={{ display: 'flex', gap: 2, justifyContent: 'center', flexWrap: 'wrap' }}>
          <Button
            variant="contained"
            size="large"
            startIcon={<BookOnlineIcon />}
            onClick={() => navigate('/login')}
            sx={{
              py: 2,
              px: 4,
              fontSize: '1.1rem',
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
            Book Now
          </Button>
          <Button
            variant="outlined"
            size="large"
            endIcon={<ArrowForwardIcon />}
            onClick={() => navigate('/help')}
            sx={{
              py: 2,
              px: 4,
              fontSize: '1.1rem',
              fontWeight: 600,
              borderRadius: 2,
              borderColor: '#2563eb',
              color: '#2563eb',
              '&:hover': {
                borderColor: '#1e3a8a',
                background: 'rgba(37, 99, 235, 0.1)',
                transform: 'translateY(-2px)',
                transition: 'all 0.3s ease'
              }
            }}
          >
            Learn More
          </Button>
        </Box>
      </Box>

      <Container maxWidth="xl">
        {/* Features Section */}
        <Box sx={{ mb: 8 }}>
          <Typography variant="h4" sx={{ 
            fontWeight: 700, 
            color: '#e5e7eb', 
            mb: 4, 
            textAlign: 'center',
            textShadow: '0 0 20px rgba(37, 99, 235, 0.3)'
          }}>
            Resort Amenities
          </Typography>
          <Grid container spacing={3}>
            {features.map((feature, idx) => (
              <Grid item xs={12} sm={6} md={4} key={idx}>
                <Card sx={{
                  height: '100%',
                  background: 'rgba(10, 25, 41, 0.8)',
                  backdropFilter: 'blur(20px)',
                  borderRadius: 3,
                  border: '1px solid rgba(37, 99, 235, 0.2)',
                  transition: 'all 0.3s ease',
                  '&:hover': {
                    transform: 'translateY(-5px)',
                    boxShadow: '0 8px 30px rgba(37, 99, 235, 0.3)',
                    border: '1px solid rgba(37, 99, 235, 0.4)'
                  }
                }}>
                  <CardContent sx={{ textAlign: 'center', py: 4 }}>
                    <Box sx={{ 
                      color: '#2563eb', 
                      fontSize: 50, 
                      mb: 2,
                      filter: 'drop-shadow(0 0 15px rgba(37, 99, 235, 0.5))'
                    }}>
                      {feature.icon}
                    </Box>
                    <Typography variant="h6" sx={{ fontWeight: 700, color: '#e5e7eb', mb: 1 }}>
                      {feature.title}
                    </Typography>
                    <Typography variant="body2" sx={{ color: '#9ca3af' }}>
                      {feature.desc}
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Box>

        {/* Rooms Section */}
        <Box sx={{ mb: 8 }}>
          <Typography variant="h4" sx={{ 
            fontWeight: 700, 
            color: '#e5e7eb', 
            mb: 4, 
            textAlign: 'center',
            textShadow: '0 0 20px rgba(37, 99, 235, 0.3)'
          }}>
            Our Rooms
          </Typography>
          <Grid container spacing={3}>
            {rooms.map((room, idx) => (
              <Grid item xs={12} sm={6} md={3} key={idx}>
                <Card sx={{
                  height: '100%',
                  background: 'rgba(10, 25, 41, 0.8)',
                  backdropFilter: 'blur(20px)',
                  borderRadius: 3,
                  border: `1px solid ${room.color}40`,
                  transition: 'all 0.3s ease',
                  '&:hover': {
                    transform: 'translateY(-5px)',
                    boxShadow: `0 8px 30px ${room.color}60`,
                    border: `1px solid ${room.color}80`
                  }
                }}>
                  <CardContent sx={{ textAlign: 'center', py: 4 }}>
                    <HotelIcon sx={{ 
                      fontSize: 50, 
                      color: room.color, 
                      mb: 2,
                      filter: `drop-shadow(0 0 15px ${room.color}80)`
                    }} />
                    <Typography variant="h6" sx={{ fontWeight: 700, color: '#e5e7eb', mb: 1 }}>
                      {room.type}
                    </Typography>
                    <Typography variant="h4" sx={{ 
                      fontWeight: 700, 
                      color: room.color, 
                      mb: 1,
                      textShadow: `0 0 15px ${room.color}80`
                    }}>
                      LKR {room.price}
                    </Typography>
                    <Typography variant="body2" sx={{ color: '#9ca3af', mb: 2 }}>
                      per night
                    </Typography>
                    <Typography variant="body2" sx={{ color: '#9ca3af' }}>
                      {room.desc}
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Box>

        {/* CTA Section */}
        <Paper sx={{
          p: 6,
          textAlign: 'center',
          background: 'linear-gradient(135deg, rgba(30, 58, 138, 0.2) 0%, rgba(37, 99, 235, 0.2) 100%)',
          backdropFilter: 'blur(20px)',
          borderRadius: 4,
          border: '1px solid rgba(37, 99, 235, 0.3)',
          mb: 6
        }}>
          <StarIcon sx={{ fontSize: 60, color: '#f59e0b', mb: 2 }} />
          <Typography variant="h4" sx={{ fontWeight: 700, color: '#e5e7eb', mb: 2 }}>
            Ready to Book Your Stay?
          </Typography>
          <Typography variant="h6" sx={{ color: '#9ca3af', mb: 4 }}>
            Experience the best hospitality and comfort at Ocean View Resort
          </Typography>
          <Button
            variant="contained"
            size="large"
            startIcon={<BookOnlineIcon />}
            onClick={() => navigate('/login')}
            sx={{
              py: 2,
              px: 5,
              fontSize: '1.1rem',
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
            Get Started
          </Button>
        </Paper>
      </Container>
    </Box>
  )
}
