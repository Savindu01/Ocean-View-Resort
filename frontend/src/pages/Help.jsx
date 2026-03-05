import React from 'react'
import {
  Container, Box, Typography, Paper, List, ListItem, ListItemText,
  Accordion, AccordionSummary, AccordionDetails, Card, CardContent, Grid, Chip
} from '@mui/material'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import HelpOutlineIcon from '@mui/icons-material/HelpOutline'
import BookOnlineIcon from '@mui/icons-material/BookOnline'
import VisibilityIcon from '@mui/icons-material/Visibility'
import ReceiptIcon from '@mui/icons-material/Receipt'
import HotelIcon from '@mui/icons-material/Hotel'
import SecurityIcon from '@mui/icons-material/Security'
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer'
import SupportAgentIcon from '@mui/icons-material/SupportAgent'

export default function Help() {
  return (
    <Container maxWidth="lg" sx={{ py: 4 }} className="fade-in">
      <Box sx={{ textAlign: 'center', mb: 5 }}>
        <HelpOutlineIcon sx={{ fontSize: 70, color: '#2563eb', mb: 2, filter: 'drop-shadow(0 0 30px rgba(37, 99, 235, 0.6))' }} />
        <Typography variant="h3" sx={{ fontWeight: 700, color: '#e5e7eb', mb: 2, textShadow: '0 0 30px rgba(37, 99, 235, 0.5)' }}>
          Help & Documentation
        </Typography>
        <Typography variant="h6" sx={{ color: '#9ca3af' }}>
          Everything you need to know about Ocean View Resort Management System
        </Typography>
      </Box>

      <Paper sx={{ 
        p: 4, mb: 4, borderRadius: 3,
        background: 'rgba(10, 25, 41, 0.8)', backdropFilter: 'blur(20px)',
        boxShadow: '0 8px 32px rgba(0,0,0,0.5)',
        border: '1px solid rgba(37, 99, 235, 0.2)'
      }}>
        <Typography variant="h5" gutterBottom sx={{ fontWeight: 700, color: '#2563eb', textShadow: '0 0 20px rgba(37, 99, 235, 0.5)' }}>
          Welcome to Ocean View Resort Management System
        </Typography>
        <Typography paragraph sx={{ fontSize: '1.1rem', color: '#d1d5db' }}>
          This comprehensive system helps manage hotel reservations efficiently with a modern, intuitive interface. 
          Below is a complete guide to using all features.
        </Typography>
      </Paper>

      {/* Quick Stats */}
      <Grid container spacing={3} sx={{ mb: 4 }}>
        {[
          { icon: <BookOnlineIcon />, title: 'Easy Booking', desc: 'Create reservations in seconds', color: '#3b82f6' },
          { icon: <VisibilityIcon />, title: 'Real-time View', desc: 'See all bookings instantly', color: '#10b981' },
          { icon: <ReceiptIcon />, title: 'Quick Billing', desc: 'Generate bills with one click', color: '#f59e0b' },
          { icon: <HotelIcon />, title: '4 Room Types', desc: 'Single, Double, Family, Suite', color: '#1e3a8a' }
        ].map((item, idx) => (
          <Grid item xs={12} sm={6} md={3} key={idx}>
            <Card sx={{ 
              height: '100%', borderRadius: 3, transition: 'all 0.3s ease',
              background: 'rgba(10, 25, 41, 0.8)', backdropFilter: 'blur(20px)',
              border: `1px solid ${item.color}40`,
              '&:hover': { transform: 'translateY(-5px)', boxShadow: `0 8px 30px ${item.color}60` }
            }}>
              <CardContent sx={{ textAlign: 'center' }}>
                <Box sx={{ color: item.color, fontSize: 50, mb: 1, filter: `drop-shadow(0 0 15px ${item.color}80)` }}>{item.icon}</Box>
                <Typography variant="h6" sx={{ fontWeight: 700, mb: 1, color: '#e5e7eb' }}>{item.title}</Typography>
                <Typography variant="body2" sx={{ color: '#9ca3af' }}>{item.desc}</Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      <Box sx={{ mb: 4 }}>
        <Accordion defaultExpanded sx={{ borderRadius: 2, mb: 2, '&:before': { display: 'none' }, background: 'rgba(10, 25, 41, 0.8)', backdropFilter: 'blur(20px)', border: '1px solid rgba(37, 99, 235, 0.2)' }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon sx={{ color: 'white' }} />} sx={{ 
            background: 'linear-gradient(135deg, #2563eb 0%, #1e3a8a 100%)', 
            color: 'white', borderRadius: 2,
            '&:hover': { opacity: 0.9 }
          }}>
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
              <BookOnlineIcon sx={{ mr: 2 }} />
              <Typography variant="h6" sx={{ fontWeight: 600 }}>Getting Started</Typography>
            </Box>
          </AccordionSummary>
          <AccordionDetails sx={{ p: 3 }}>
            <Typography paragraph sx={{ fontSize: '1rem', color: '#d1d5db' }}>
              <strong style={{ color: '#2563eb' }}>Login:</strong> Use your credentials to access the system. Default admin account:
            </Typography>
            <Box sx={{ ml: 3, mb: 2 }}>
              <Chip label="Username: admin" sx={{ mr: 1, mb: 1, background: 'rgba(37, 99, 235, 0.2)', color: '#a5b4fc' }} />
              <Chip label="Password: admin123" sx={{ mb: 1, background: 'rgba(37, 99, 235, 0.2)', color: '#a5b4fc' }} />
            </Box>
            <Typography paragraph sx={{ fontSize: '1rem', color: '#d1d5db' }}>
              <strong style={{ color: '#2563eb' }}>Dashboard:</strong> After login, you'll access the main reservation management interface with:
            </Typography>
            <List>
              <ListItem><ListItemText primary="✓ Add New Reservation" secondary="Create bookings for guests" primaryTypographyProps={{ color: '#e5e7eb' }} secondaryTypographyProps={{ color: '#9ca3af' }} /></ListItem>
              <ListItem><ListItemText primary="✓ View Reservations" secondary="Browse all bookings with advanced filters" primaryTypographyProps={{ color: '#e5e7eb' }} secondaryTypographyProps={{ color: '#9ca3af' }} /></ListItem>
              <ListItem><ListItemText primary="✓ Generate Bills" secondary="Calculate and print guest invoices" primaryTypographyProps={{ color: '#e5e7eb' }} secondaryTypographyProps={{ color: '#9ca3af' }} /></ListItem>
              <ListItem><ListItemText primary="✓ Search & Filter" secondary="Find reservations by name, room type, or dates" primaryTypographyProps={{ color: '#e5e7eb' }} secondaryTypographyProps={{ color: '#9ca3af' }} /></ListItem>
            </List>
          </AccordionDetails>
        </Accordion>

        <Accordion sx={{ borderRadius: 2, mb: 2, '&:before': { display: 'none' }, background: 'rgba(10, 25, 41, 0.8)', backdropFilter: 'blur(20px)', border: '1px solid rgba(59, 130, 246, 0.2)' }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon sx={{ color: 'white' }} />} sx={{ 
            background: 'linear-gradient(135deg, #3b82f6 0%, #2563eb 100%)', 
            color: 'white', borderRadius: 2 
          }}>
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
              <HotelIcon sx={{ mr: 2 }} />
              <Typography variant="h6" sx={{ fontWeight: 600 }}>Room Types & Rates</Typography>
            </Box>
          </AccordionSummary>
          <AccordionDetails sx={{ p: 3 }}>
            <Grid container spacing={2}>
              {[
                { type: 'Single Room', rate: '5,000', desc: 'Basic room for one guest', color: '#3b82f6' },
                { type: 'Double Room', rate: '8,000', desc: 'Room with double bed for two', color: '#10b981' },
                { type: 'Family Room', rate: '12,000', desc: 'Spacious room for families', color: '#f59e0b' },
                { type: 'Suite', rate: '20,000', desc: 'Luxury suite with premium amenities', color: '#1e3a8a' }
              ].map((room, idx) => (
                <Grid item xs={12} sm={6} key={idx}>
                  <Card sx={{ borderLeft: `4px solid ${room.color}`, height: '100%', background: 'rgba(0, 0, 0, 0.5)', border: `1px solid ${room.color}40` }}>
                    <CardContent>
                      <Typography variant="h6" sx={{ fontWeight: 700, color: room.color, textShadow: `0 0 15px ${room.color}80` }}>{room.type}</Typography>
                      <Typography variant="h5" sx={{ fontWeight: 700, my: 1, color: '#e5e7eb' }}>LKR {room.rate}/night</Typography>
                      <Typography variant="body2" sx={{ color: '#9ca3af' }}>{room.desc}</Typography>
                    </CardContent>
                  </Card>
                </Grid>
              ))}
            </Grid>
          </AccordionDetails>
        </Accordion>

        <Accordion sx={{ borderRadius: 2, mb: 2, '&:before': { display: 'none' }, background: 'rgba(10, 25, 41, 0.8)', backdropFilter: 'blur(20px)', border: '1px solid rgba(16, 185, 129, 0.2)' }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon sx={{ color: 'white' }} />} sx={{ 
            background: 'linear-gradient(135deg, #10b981 0%, #059669 100%)', 
            color: 'white', borderRadius: 2 
          }}>
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
              <SecurityIcon sx={{ mr: 2 }} />
              <Typography variant="h6" sx={{ fontWeight: 600 }}>Validation Rules</Typography>
            </Box>
          </AccordionSummary>
          <AccordionDetails sx={{ p: 3 }}>
            <List>
              <ListItem><ListItemText primary="✓ Guest Name" secondary="Required, cannot be empty" primaryTypographyProps={{ color: '#e5e7eb' }} secondaryTypographyProps={{ color: '#9ca3af' }} /></ListItem>
              <ListItem><ListItemText primary="✓ Address" secondary="Required, minimum details needed" primaryTypographyProps={{ color: '#e5e7eb' }} secondaryTypographyProps={{ color: '#9ca3af' }} /></ListItem>
              <ListItem><ListItemText primary="✓ Contact Number" secondary="Required, valid phone format" primaryTypographyProps={{ color: '#e5e7eb' }} secondaryTypographyProps={{ color: '#9ca3af' }} /></ListItem>
              <ListItem><ListItemText primary="✓ Check-in Date" secondary="Cannot be in the past" primaryTypographyProps={{ color: '#e5e7eb' }} secondaryTypographyProps={{ color: '#9ca3af' }} /></ListItem>
              <ListItem><ListItemText primary="✓ Check-out Date" secondary="Must be after check-in date" primaryTypographyProps={{ color: '#e5e7eb' }} secondaryTypographyProps={{ color: '#9ca3af' }} /></ListItem>
              <ListItem><ListItemText primary="✓ Room Availability" secondary="System prevents overlapping bookings" primaryTypographyProps={{ color: '#e5e7eb' }} secondaryTypographyProps={{ color: '#9ca3af' }} /></ListItem>
            </List>
          </AccordionDetails>
        </Accordion>

        <Accordion sx={{ borderRadius: 2, mb: 2, '&:before': { display: 'none' }, background: 'rgba(10, 25, 41, 0.8)', backdropFilter: 'blur(20px)', border: '1px solid rgba(245, 158, 11, 0.2)' }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon sx={{ color: 'white' }} />} sx={{ 
            background: 'linear-gradient(135deg, #f59e0b 0%, #d97706 100%)', 
            color: 'white', borderRadius: 2 
          }}>
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
              <QuestionAnswerIcon sx={{ mr: 2 }} />
              <Typography variant="h6" sx={{ fontWeight: 600 }}>Frequently Asked Questions</Typography>
            </Box>
          </AccordionSummary>
          <AccordionDetails sx={{ p: 3 }}>
            {[
              { q: 'How can I cancel a reservation?', a: 'Go to Reservations, find the booking, and click the delete button. Confirm the cancellation.' },
              { q: 'Can I modify dates after booking?', a: 'Yes. Click the edit button and update the dates. The system will check availability.' },
              { q: 'How is the bill calculated?', a: 'Total = Number of nights × Room rate per night. Calculated automatically.' },
              { q: 'What if a room is already booked?', a: 'The system will show an error. Choose a different room type or date range.' },
              { q: 'How do I print a bill?', a: 'Click the bill icon, then use your browser\'s print function (Ctrl+P or Cmd+P).' }
            ].map((faq, idx) => (
              <Box key={idx} sx={{ mb: 3 }}>
                <Typography paragraph sx={{ fontWeight: 700, color: '#2563eb' }}>Q: {faq.q}</Typography>
                <Typography paragraph sx={{ ml: 2, color: '#d1d5db' }}>A: {faq.a}</Typography>
              </Box>
            ))}
          </AccordionDetails>
        </Accordion>

        <Accordion sx={{ borderRadius: 2, '&:before': { display: 'none' }, background: 'rgba(10, 25, 41, 0.8)', backdropFilter: 'blur(20px)', border: '1px solid rgba(139, 92, 246, 0.2)' }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon sx={{ color: 'white' }} />} sx={{ 
            background: 'linear-gradient(135deg, #1e3a8a 0%, #7c3aed 100%)', 
            color: 'white', borderRadius: 2 
          }}>
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
              <SupportAgentIcon sx={{ mr: 2 }} />
              <Typography variant="h6" sx={{ fontWeight: 600 }}>Technical Support</Typography>
            </Box>
          </AccordionSummary>
          <AccordionDetails sx={{ p: 3 }}>
            <Typography paragraph sx={{ fontSize: '1rem', color: '#d1d5db' }}>
              <strong style={{ color: '#1e3a8a' }}>Need Help?</strong> If you encounter any issues, please provide:
            </Typography>
            <List>
              <ListItem><ListItemText primary="• Your username" primaryTypographyProps={{ color: '#e5e7eb' }} /></ListItem>
              <ListItem><ListItemText primary="• Action you were performing" primaryTypographyProps={{ color: '#e5e7eb' }} /></ListItem>
              <ListItem><ListItemText primary="• Exact error message (if any)" primaryTypographyProps={{ color: '#e5e7eb' }} /></ListItem>
              <ListItem><ListItemText primary="• Steps to reproduce the issue" primaryTypographyProps={{ color: '#e5e7eb' }} /></ListItem>
            </List>
            <Paper sx={{ mt: 2, p: 3, background: 'rgba(37, 99, 235, 0.1)', borderRadius: 2, textAlign: 'center', border: '1px solid rgba(37, 99, 235, 0.3)' }}>
              <Typography variant="body1" sx={{ fontWeight: 600, color: '#a5b4fc' }}>
                Contact IT Department for prompt assistance
              </Typography>
            </Paper>
          </AccordionDetails>
        </Accordion>
      </Box>
    </Container>
  )
}
