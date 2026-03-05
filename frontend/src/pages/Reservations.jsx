import React, { useEffect, useState } from 'react'
import api from '../utils/api'

// MUI imports
import {
  Container, Box, Typography, Paper, Table, TableHead, TableBody, TableRow, TableCell,
  CircularProgress, TextField, Select, MenuItem, InputLabel, FormControl, Button,
  Dialog, DialogTitle, DialogContent, DialogActions, Alert, IconButton, Tooltip, Grid,
  Chip, Card, CardContent, Fade, Zoom
} from '@mui/material'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import DescriptionIcon from '@mui/icons-material/Description'
import SearchIcon from '@mui/icons-material/Search'
import AddIcon from '@mui/icons-material/Add'
import FilterListIcon from '@mui/icons-material/FilterList'
import HotelIcon from '@mui/icons-material/Hotel'
import PersonIcon from '@mui/icons-material/Person'
import PhoneIcon from '@mui/icons-material/Phone'
import HomeIcon from '@mui/icons-material/Home'
import CalendarTodayIcon from '@mui/icons-material/CalendarToday'

export default function Reservations() {
  const [list, setList] = useState([])
  const [filteredList, setFilteredList] = useState([])
  const [loading, setLoading] = useState(true)
  const [showAddForm, setShowAddForm] = useState(false)
  const [editingId, setEditingId] = useState(null)
  const [showBill, setShowBill] = useState(null)
  const [error, setError] = useState(null)
  const [success, setSuccess] = useState(null)
  
  const [form, setForm] = useState({ 
    guestName: '', address: '', contactNumber: '', roomType: 'SINGLE', 
    checkInDate: '', checkOutDate: '' 
  })
  
  const [searchFilters, setSearchFilters] = useState({
    guestName: '', roomType: '', fromDate: '', toDate: ''
  })

  useEffect(() => { fetchList() }, [])
  useEffect(() => { applyFilters() }, [list, searchFilters])

  async function fetchList() {
    setLoading(true)
    try {
      const data = await api.get('/api/reservations')
      setList(data || [])
      setError(null)
    } catch (err) {
      setError('Failed to fetch reservations')
    }
    setLoading(false)
  }

  function applyFilters() {
    let filtered = list
    if (searchFilters.guestName) {
      filtered = filtered.filter(r => r.guestName.toLowerCase().includes(searchFilters.guestName.toLowerCase()))
    }
    if (searchFilters.roomType) {
      filtered = filtered.filter(r => r.roomType === searchFilters.roomType)
    }
    if (searchFilters.fromDate) {
      filtered = filtered.filter(r => r.checkInDate >= searchFilters.fromDate)
    }
    if (searchFilters.toDate) {
      filtered = filtered.filter(r => r.checkOutDate <= searchFilters.toDate)
    }
    setFilteredList(filtered)
  }

  async function submit(e) {
    e.preventDefault()
    setError(null)
    if (!form.guestName || !form.address || !form.contactNumber || !form.checkInDate || !form.checkOutDate) {
      setError('All fields are required')
      return
    }
    try {
      if (editingId) {
        await api.put(`/api/reservations/${editingId}`, form)
        setSuccess('Reservation updated successfully')
      } else {
        await api.post('/api/reservations', form)
        setSuccess('Reservation created successfully')
      }
      await fetchList()
      resetForm()
    } catch (err) {
      setError(err.message || 'Failed to save reservation')
    }
  }

  async function handleEdit(reservation) {
    setForm({
      guestName: reservation.guestName, address: reservation.address,
      contactNumber: reservation.contactNumber, roomType: reservation.roomType,
      checkInDate: reservation.checkInDate, checkOutDate: reservation.checkOutDate
    })
    setEditingId(reservation.reservationNumber)
    setShowAddForm(true)
  }

  async function handleDelete(resNum) {
    if (window.confirm('Are you sure you want to delete this reservation?')) {
      try {
        await api.delete(`/api/reservations/${resNum}`)
        setSuccess('Reservation deleted successfully')
        await fetchList()
      } catch (err) {
        setError('Failed to delete reservation')
      }
    }
  }

  function handleViewBill(reservation) { setShowBill(reservation) }

  function resetForm() {
    setForm({ guestName: '', address: '', contactNumber: '', roomType: 'SINGLE', checkInDate: '', checkOutDate: '' })
    setEditingId(null)
    setShowAddForm(false)
  }

  function calculateNights(checkIn, checkOut) {
    const from = new Date(checkIn)
    const to = new Date(checkOut)
    return Math.floor((to - from) / (1000 * 60 * 60 * 24))
  }

  function calculateTotal(roomType, nights) {
    const rates = { SINGLE: 5000, DOUBLE: 8000, FAMILY: 12000, SUITE: 20000 }
    return (rates[roomType] || 0) * nights
  }

  const getRoomColor = (roomType) => {
    const colors = { SINGLE: '#3b82f6', DOUBLE: '#10b981', FAMILY: '#f59e0b', SUITE: '#1e3a8a' }
    return colors[roomType] || '#2563eb'
  }

  const stats = {
    total: list.length,
    single: list.filter(r => r.roomType === 'SINGLE').length,
    double: list.filter(r => r.roomType === 'DOUBLE').length,
    family: list.filter(r => r.roomType === 'FAMILY').length,
    suite: list.filter(r => r.roomType === 'SUITE').length
  }

  return (
    <Container maxWidth="xl" sx={{ py: 4 }} className="fade-in">
      {error && <Alert severity="error" sx={{ mb: 2, borderRadius: 2, background: 'rgba(239, 68, 68, 0.1)', border: '1px solid rgba(239, 68, 68, 0.3)', color: '#fca5a5' }} onClose={() => setError(null)}>{error}</Alert>}
      {success && <Alert severity="success" sx={{ mb: 2, borderRadius: 2, background: 'rgba(16, 185, 129, 0.1)', border: '1px solid rgba(16, 185, 129, 0.3)', color: '#6ee7b7' }} onClose={() => setSuccess(null)}>{success}</Alert>}
      
      {/* Header with Stats */}
      <Box sx={{ mb: 4 }}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
          <Typography variant="h4" sx={{ fontWeight: 700, color: '#e5e7eb', textShadow: '0 0 30px rgba(37, 99, 235, 0.5)' }}>
            Reservations Dashboard
          </Typography>
          <Button 
            variant="contained" 
            startIcon={<AddIcon />}
            onClick={() => setShowAddForm(true)}
            sx={{ 
              py: 1.5, px: 3, borderRadius: 2, fontWeight: 600,
              background: 'linear-gradient(135deg, #2563eb 0%, #1e3a8a 100%)',
              boxShadow: '0 4px 15px rgba(37, 99, 235, 0.4)',
              '&:hover': {
                background: 'linear-gradient(135deg, #1e3a8a 0%, #2563eb 100%)',
                transform: 'translateY(-2px)', boxShadow: '0 6px 20px rgba(37, 99, 235, 0.6)',
                transition: 'all 0.3s ease'
              }
            }}
          >
            New Reservation
          </Button>
        </Box>

        {/* Stats Cards */}
        <Grid container spacing={2}>
          {[
            { label: 'Total', value: stats.total, color: '#2563eb', icon: <HotelIcon /> },
            { label: 'Single', value: stats.single, color: '#3b82f6', icon: <PersonIcon /> },
            { label: 'Double', value: stats.double, color: '#10b981', icon: <PersonIcon /> },
            { label: 'Family', value: stats.family, color: '#f59e0b', icon: <PersonIcon /> },
            { label: 'Suite', value: stats.suite, color: '#1e3a8a', icon: <PersonIcon /> }
          ].map((stat, idx) => (
            <Grid item xs={12} sm={6} md={2.4} key={idx}>
              <Zoom in timeout={300 + idx * 100}>
                <Card sx={{ 
                  background: 'rgba(10, 25, 41, 0.8)', backdropFilter: 'blur(20px)',
                  borderRadius: 3, boxShadow: '0 4px 20px rgba(0,0,0,0.3)',
                  border: `1px solid ${stat.color}40`,
                  transition: 'all 0.3s ease',
                  '&:hover': { transform: 'translateY(-5px)', boxShadow: `0 8px 30px ${stat.color}60` }
                }}>
                  <CardContent>
                    <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                      <Box>
                        <Typography variant="body2" sx={{ color: '#9ca3af', fontWeight: 600 }}>
                          {stat.label}
                        </Typography>
                        <Typography variant="h4" sx={{ fontWeight: 700, color: stat.color, textShadow: `0 0 20px ${stat.color}80` }}>
                          {stat.value}
                        </Typography>
                      </Box>
                      <Box sx={{ color: stat.color, opacity: 0.3, fontSize: 40 }}>
                        {stat.icon}
                      </Box>
                    </Box>
                  </CardContent>
                </Card>
              </Zoom>
            </Grid>
          ))}
        </Grid>
      </Box>

      {/* Search & Filter */}
      <Fade in timeout={600}>
        <Paper sx={{ 
          p: 3, mb: 3, borderRadius: 3,
          background: 'rgba(10, 25, 41, 0.8)', backdropFilter: 'blur(20px)',
          boxShadow: '0 4px 20px rgba(0,0,0,0.3)',
          border: '1px solid rgba(37, 99, 235, 0.2)'
        }}>
          <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
            <FilterListIcon sx={{ mr: 1, color: '#2563eb' }} />
            <Typography variant="h6" sx={{ fontWeight: 600, color: '#e5e7eb' }}>Search & Filter</Typography>
          </Box>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6} md={3}>
              <TextField label="Guest Name" fullWidth value={searchFilters.guestName}
                onChange={e => setSearchFilters({ ...searchFilters, guestName: e.target.value })}
                InputProps={{ startAdornment: <SearchIcon sx={{ mr: 1, color: '#2563eb' }} /> }}
                sx={{ 
                  '& .MuiOutlinedInput-root': { 
                    background: 'rgba(0, 0, 0, 0.5)', color: '#e5e7eb',
                    '& fieldset': { borderColor: 'rgba(37, 99, 235, 0.3)' },
                    '&:hover fieldset': { borderColor: '#2563eb' }, 
                    '&.Mui-focused fieldset': { borderColor: '#2563eb' } 
                  },
                  '& .MuiInputLabel-root': { color: '#9ca3af' },
                  '& .MuiInputLabel-root.Mui-focused': { color: '#2563eb' }
                }}
              />
            </Grid>
            <Grid item xs={12} sm={6} md={3}>
              <FormControl fullWidth>
                <InputLabel sx={{ color: '#9ca3af', '&.Mui-focused': { color: '#2563eb' } }}>Room Type</InputLabel>
                <Select label="Room Type" value={searchFilters.roomType}
                  onChange={e => setSearchFilters({ ...searchFilters, roomType: e.target.value })}
                  sx={{
                    background: 'rgba(0, 0, 0, 0.5)', color: '#e5e7eb',
                    '& .MuiOutlinedInput-notchedOutline': { borderColor: 'rgba(37, 99, 235, 0.3)' },
                    '&:hover .MuiOutlinedInput-notchedOutline': { borderColor: '#2563eb' },
                    '&.Mui-focused .MuiOutlinedInput-notchedOutline': { borderColor: '#2563eb' },
                    '& .MuiSelect-select': { 
                      padding: '16.5px 14px',
                      fontSize: '1rem'
                    }
                  }}
                  MenuProps={{
                    PaperProps: {
                      sx: {
                        background: 'rgba(10, 25, 41, 0.95)',
                        backdropFilter: 'blur(20px)',
                        border: '1px solid rgba(37, 99, 235, 0.3)',
                        '& .MuiMenuItem-root': {
                          color: '#e5e7eb',
                          fontSize: '1rem',
                          padding: '12px 16px',
                          '&:hover': {
                            background: 'rgba(37, 99, 235, 0.2)'
                          },
                          '&.Mui-selected': {
                            background: 'rgba(37, 99, 235, 0.3)',
                            '&:hover': {
                              background: 'rgba(37, 99, 235, 0.4)'
                            }
                          }
                        }
                      }
                    }
                  }}
                >
                  <MenuItem value="">All Rooms</MenuItem>
                  <MenuItem value="SINGLE">Single</MenuItem>
                  <MenuItem value="DOUBLE">Double</MenuItem>
                  <MenuItem value="FAMILY">Family</MenuItem>
                  <MenuItem value="SUITE">Suite</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12} sm={6} md={3}>
              <TextField label="From Date" type="date" fullWidth InputLabelProps={{ shrink: true }}
                value={searchFilters.fromDate}
                onChange={e => setSearchFilters({ ...searchFilters, fromDate: e.target.value })}
                sx={{ 
                  '& .MuiOutlinedInput-root': { 
                    background: 'rgba(0, 0, 0, 0.5)', color: '#e5e7eb',
                    '& fieldset': { borderColor: 'rgba(37, 99, 235, 0.3)' },
                    '&:hover fieldset': { borderColor: '#2563eb' }, 
                    '&.Mui-focused fieldset': { borderColor: '#2563eb' } 
                  },
                  '& .MuiInputLabel-root': { color: '#9ca3af' },
                  '& .MuiInputLabel-root.Mui-focused': { color: '#2563eb' }
                }}
              />
            </Grid>
            <Grid item xs={12} sm={6} md={3}>
              <TextField label="To Date" type="date" fullWidth InputLabelProps={{ shrink: true }}
                value={searchFilters.toDate}
                onChange={e => setSearchFilters({ ...searchFilters, toDate: e.target.value })}
                sx={{ 
                  '& .MuiOutlinedInput-root': { 
                    background: 'rgba(0, 0, 0, 0.5)', color: '#e5e7eb',
                    '& fieldset': { borderColor: 'rgba(37, 99, 235, 0.3)' },
                    '&:hover fieldset': { borderColor: '#2563eb' }, 
                    '&.Mui-focused fieldset': { borderColor: '#2563eb' } 
                  },
                  '& .MuiInputLabel-root': { color: '#9ca3af' },
                  '& .MuiInputLabel-root.Mui-focused': { color: '#2563eb' }
                }}
              />
            </Grid>
          </Grid>
        </Paper>
      </Fade>

      {/* Reservations Table */}
      {loading ? (
        <Box sx={{ display: 'flex', justifyContent: 'center', py: 8 }}>
          <CircularProgress sx={{ color: '#2563eb' }} size={60} />
        </Box>
      ) : (
        <Fade in timeout={800}>
          <Paper sx={{ 
            overflowX: 'auto', borderRadius: 3,
            background: 'rgba(10, 25, 41, 0.8)', backdropFilter: 'blur(20px)',
            boxShadow: '0 4px 20px rgba(0,0,0,0.3)',
            border: '1px solid rgba(37, 99, 235, 0.2)'
          }}>
            <Table>
              <TableHead>
                <TableRow sx={{ background: 'linear-gradient(135deg, #2563eb 0%, #1e3a8a 100%)' }}>
                  <TableCell sx={{ color: 'white', fontWeight: 700 }}>Res. #</TableCell>
                  <TableCell sx={{ color: 'white', fontWeight: 700 }}>Guest</TableCell>
                  <TableCell sx={{ color: 'white', fontWeight: 700 }}>Room</TableCell>
                  <TableCell sx={{ color: 'white', fontWeight: 700 }}>Check-in</TableCell>
                  <TableCell sx={{ color: 'white', fontWeight: 700 }}>Check-out</TableCell>
                  <TableCell align="right" sx={{ color: 'white', fontWeight: 700 }}>Total</TableCell>
                  <TableCell align="center" sx={{ color: 'white', fontWeight: 700 }}>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {filteredList.length > 0 ? (
                  filteredList.map((r, idx) => (
                    <Fade in timeout={300 + idx * 50} key={r.reservationNumber}>
                      <TableRow hover sx={{ 
                        transition: 'all 0.2s ease',
                        '&:hover': { background: 'rgba(37, 99, 235, 0.1)', transform: 'scale(1.01)' }
                      }}>
                        <TableCell><Chip label={r.reservationNumber} size="small" sx={{ fontFamily: 'monospace', fontWeight: 600, background: 'rgba(37, 99, 235, 0.2)', color: '#a5b4fc' }} /></TableCell>
                        <TableCell sx={{ fontWeight: 600, color: '#e5e7eb' }}>{r.guestName}</TableCell>
                        <TableCell>
                          <Chip label={r.roomType} size="small" sx={{ 
                            background: getRoomColor(r.roomType), color: 'white', fontWeight: 600,
                            boxShadow: `0 0 15px ${getRoomColor(r.roomType)}60`
                          }} />
                        </TableCell>
                        <TableCell sx={{ color: '#9ca3af' }}>{r.checkInDate}</TableCell>
                        <TableCell sx={{ color: '#9ca3af' }}>{r.checkOutDate}</TableCell>
                        <TableCell align="right" sx={{ fontWeight: 700, color: '#2563eb', textShadow: '0 0 10px rgba(37, 99, 235, 0.5)' }}>
                          LKR {(r.totalAmount || calculateTotal(r.roomType, calculateNights(r.checkInDate, r.checkOutDate))).toLocaleString()}
                        </TableCell>
                        <TableCell align="center">
                          <Tooltip title="View Bill">
                            <IconButton size="small" onClick={() => handleViewBill(r)} sx={{ color: '#3b82f6', '&:hover': { background: 'rgba(59, 130, 246, 0.2)' } }}>
                              <DescriptionIcon />
                            </IconButton>
                          </Tooltip>
                          <Tooltip title="Edit">
                            <IconButton size="small" onClick={() => handleEdit(r)} sx={{ color: '#10b981', '&:hover': { background: 'rgba(16, 185, 129, 0.2)' } }}>
                              <EditIcon />
                            </IconButton>
                          </Tooltip>
                          <Tooltip title="Delete">
                            <IconButton size="small" onClick={() => handleDelete(r.reservationNumber)} sx={{ color: '#ef4444', '&:hover': { background: 'rgba(239, 68, 68, 0.2)' } }}>
                              <DeleteIcon />
                            </IconButton>
                          </Tooltip>
                        </TableCell>
                      </TableRow>
                    </Fade>
                  ))
                ) : (
                  <TableRow>
                    <TableCell colSpan={7} align="center" sx={{ py: 8 }}>
                      <HotelIcon sx={{ fontSize: 60, color: '#374151', mb: 2 }} />
                      <Typography sx={{ color: '#6b7280' }} variant="h6">No reservations found</Typography>
                    </TableCell>
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </Paper>
        </Fade>
      )}

      {/* Add/Edit Form Dialog */}
      <Dialog open={showAddForm} onClose={resetForm} maxWidth="sm" fullWidth PaperProps={{ sx: { borderRadius: 3, background: 'rgba(10, 25, 41, 0.95)', backdropFilter: 'blur(20px)', border: '1px solid rgba(37, 99, 235, 0.2)' } }}>
        <DialogTitle sx={{ 
          background: 'linear-gradient(135deg, #2563eb 0%, #1e3a8a 100%)', 
          color: 'white', fontWeight: 700 
        }}>
          {editingId ? 'Edit Reservation' : 'Add New Reservation'}
        </DialogTitle>
        <DialogContent sx={{ pt: 3 }}>
          <Box component="form" sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField label="Guest Name" fullWidth required value={form.guestName}
              onChange={e => setForm({ ...form, guestName: e.target.value })}
              InputProps={{ startAdornment: <PersonIcon sx={{ mr: 1, color: '#2563eb' }} /> }}
              sx={{ 
                '& .MuiOutlinedInput-root': { 
                  background: 'rgba(0, 0, 0, 0.5)', color: '#e5e7eb',
                  '& fieldset': { borderColor: 'rgba(37, 99, 235, 0.3)' },
                  '&:hover fieldset': { borderColor: '#2563eb' }, 
                  '&.Mui-focused fieldset': { borderColor: '#2563eb' } 
                },
                '& .MuiInputLabel-root': { color: '#9ca3af' },
                '& .MuiInputLabel-root.Mui-focused': { color: '#2563eb' }
              }}
            />
            <TextField label="Address" fullWidth required multiline rows={2} value={form.address}
              onChange={e => setForm({ ...form, address: e.target.value })}
              InputProps={{ startAdornment: <HomeIcon sx={{ mr: 1, color: '#2563eb', alignSelf: 'flex-start', mt: 1 }} /> }}
              sx={{ 
                '& .MuiOutlinedInput-root': { 
                  background: 'rgba(0, 0, 0, 0.5)', color: '#e5e7eb',
                  '& fieldset': { borderColor: 'rgba(37, 99, 235, 0.3)' },
                  '&:hover fieldset': { borderColor: '#2563eb' }, 
                  '&.Mui-focused fieldset': { borderColor: '#2563eb' } 
                },
                '& .MuiInputLabel-root': { color: '#9ca3af' },
                '& .MuiInputLabel-root.Mui-focused': { color: '#2563eb' }
              }}
            />
            <TextField label="Contact Number" fullWidth required value={form.contactNumber}
              onChange={e => setForm({ ...form, contactNumber: e.target.value })}
              InputProps={{ startAdornment: <PhoneIcon sx={{ mr: 1, color: '#2563eb' }} /> }}
              sx={{ 
                '& .MuiOutlinedInput-root': { 
                  background: 'rgba(0, 0, 0, 0.5)', color: '#e5e7eb',
                  '& fieldset': { borderColor: 'rgba(37, 99, 235, 0.3)' },
                  '&:hover fieldset': { borderColor: '#2563eb' }, 
                  '&.Mui-focused fieldset': { borderColor: '#2563eb' } 
                },
                '& .MuiInputLabel-root': { color: '#9ca3af' },
                '& .MuiInputLabel-root.Mui-focused': { color: '#2563eb' }
              }}
            />
            <FormControl fullWidth>
              <InputLabel sx={{ color: '#9ca3af', '&.Mui-focused': { color: '#2563eb' } }}>Room Type</InputLabel>
              <Select label="Room Type" value={form.roomType}
                onChange={e => setForm({ ...form, roomType: e.target.value })}
                sx={{
                  background: 'rgba(0, 0, 0, 0.5)', color: '#e5e7eb',
                  '& .MuiOutlinedInput-notchedOutline': { borderColor: 'rgba(37, 99, 235, 0.3)' },
                  '&:hover .MuiOutlinedInput-notchedOutline': { borderColor: '#2563eb' },
                  '&.Mui-focused .MuiOutlinedInput-notchedOutline': { borderColor: '#2563eb' },
                  '& .MuiSelect-select': { 
                    padding: '16.5px 14px',
                    fontSize: '1rem'
                  }
                }}
                MenuProps={{
                  PaperProps: {
                    sx: {
                      background: 'rgba(10, 25, 41, 0.95)',
                      backdropFilter: 'blur(20px)',
                      border: '1px solid rgba(37, 99, 235, 0.3)',
                      '& .MuiMenuItem-root': {
                        color: '#e5e7eb',
                        fontSize: '1rem',
                        padding: '12px 16px',
                        '&:hover': {
                          background: 'rgba(37, 99, 235, 0.2)'
                        },
                        '&.Mui-selected': {
                          background: 'rgba(37, 99, 235, 0.3)',
                          '&:hover': {
                            background: 'rgba(37, 99, 235, 0.4)'
                          }
                        }
                      }
                    }
                  }
                }}
              >
                <MenuItem value="SINGLE">Single (LKR 5,000/night)</MenuItem>
                <MenuItem value="DOUBLE">Double (LKR 8,000/night)</MenuItem>
                <MenuItem value="FAMILY">Family (LKR 12,000/night)</MenuItem>
                <MenuItem value="SUITE">Suite (LKR 20,000/night)</MenuItem>
              </Select>
            </FormControl>
            <TextField label="Check-in" type="date" fullWidth required InputLabelProps={{ shrink: true }}
              value={form.checkInDate} onChange={e => setForm({ ...form, checkInDate: e.target.value })}
              InputProps={{ startAdornment: <CalendarTodayIcon sx={{ mr: 1, color: '#2563eb' }} /> }}
              sx={{ 
                '& .MuiOutlinedInput-root': { 
                  background: 'rgba(0, 0, 0, 0.5)', color: '#e5e7eb',
                  '& fieldset': { borderColor: 'rgba(37, 99, 235, 0.3)' },
                  '&:hover fieldset': { borderColor: '#2563eb' }, 
                  '&.Mui-focused fieldset': { borderColor: '#2563eb' } 
                },
                '& .MuiInputLabel-root': { color: '#9ca3af' },
                '& .MuiInputLabel-root.Mui-focused': { color: '#2563eb' }
              }}
            />
            <TextField label="Check-out" type="date" fullWidth required InputLabelProps={{ shrink: true }}
              value={form.checkOutDate} onChange={e => setForm({ ...form, checkOutDate: e.target.value })}
              InputProps={{ startAdornment: <CalendarTodayIcon sx={{ mr: 1, color: '#2563eb' }} /> }}
              sx={{ 
                '& .MuiOutlinedInput-root': { 
                  background: 'rgba(0, 0, 0, 0.5)', color: '#e5e7eb',
                  '& fieldset': { borderColor: 'rgba(37, 99, 235, 0.3)' },
                  '&:hover fieldset': { borderColor: '#2563eb' }, 
                  '&.Mui-focused fieldset': { borderColor: '#2563eb' } 
                },
                '& .MuiInputLabel-root': { color: '#9ca3af' },
                '& .MuiInputLabel-root.Mui-focused': { color: '#2563eb' }
              }}
            />
          </Box>
        </DialogContent>
        <DialogActions sx={{ p: 2, background: 'rgba(0, 0, 0, 0.5)' }}>
          <Button onClick={resetForm} sx={{ color: '#9ca3af' }}>Cancel</Button>
          <Button onClick={submit} variant="contained" sx={{ 
            background: 'linear-gradient(135deg, #2563eb 0%, #1e3a8a 100%)',
            '&:hover': { background: 'linear-gradient(135deg, #1e3a8a 0%, #2563eb 100%)' }
          }}>
            {editingId ? 'Update' : 'Create'}
          </Button>
        </DialogActions>
      </Dialog>

      {/* Bill View Dialog */}
      {showBill && (
        <Dialog open={!!showBill} onClose={() => setShowBill(null)} maxWidth="sm" fullWidth PaperProps={{ sx: { borderRadius: 3, background: 'rgba(10, 25, 41, 0.95)', backdropFilter: 'blur(20px)', border: '1px solid rgba(37, 99, 235, 0.2)' } }}>
          <DialogTitle sx={{ 
            background: 'linear-gradient(135deg, #2563eb 0%, #1e3a8a 100%)', 
            color: 'white', fontWeight: 700 
          }}>
            Reservation Bill
          </DialogTitle>
          <DialogContent sx={{ pt: 3 }}>
            <Paper id="bill-print-area" sx={{ p: 3, background: 'rgba(0, 0, 0, 0.8)', borderRadius: 2, border: '1px solid rgba(37, 99, 235, 0.2)' }}>
              <Box sx={{ fontFamily: 'monospace', color: '#e5e7eb' }}>
                {/* Header */}
                <Box sx={{ textAlign: 'center', borderBottom: '2px solid #2563eb', pb: 2, mb: 3 }}>
                  <Typography sx={{ fontSize: '1.2rem', fontWeight: 700, color: '#2563eb', mb: 1 }}>
                    OCEAN VIEW RESORT
                  </Typography>
                  <Typography sx={{ fontSize: '0.9rem', color: '#9ca3af' }}>
                    INVOICE
                  </Typography>
                </Box>

                {/* Invoice Info */}
                <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3, fontSize: '0.85rem' }}>
                  <Box>
                    <Typography sx={{ color: '#9ca3af' }}>Reservation #:</Typography>
                    <Typography sx={{ fontWeight: 700 }}>{showBill.reservationNumber}</Typography>
                  </Box>
                  <Box sx={{ textAlign: 'right' }}>
                    <Typography sx={{ color: '#9ca3af' }}>Date:</Typography>
                    <Typography sx={{ fontWeight: 700 }}>{new Date().toLocaleDateString()}</Typography>
                  </Box>
                </Box>

                {/* Guest Details */}
                <Box sx={{ mb: 3, pb: 2, borderBottom: '1px solid rgba(37, 99, 235, 0.3)' }}>
                  <Typography sx={{ fontSize: '0.9rem', fontWeight: 700, color: '#2563eb', mb: 1 }}>
                    GUEST DETAILS
                  </Typography>
                  <Box sx={{ fontSize: '0.85rem', lineHeight: 1.8 }}>
                    <Box sx={{ display: 'flex' }}>
                      <Typography sx={{ width: '100px', color: '#9ca3af' }}>Name:</Typography>
                      <Typography sx={{ flex: 1 }}>{showBill.guestName}</Typography>
                    </Box>
                    <Box sx={{ display: 'flex' }}>
                      <Typography sx={{ width: '100px', color: '#9ca3af' }}>Address:</Typography>
                      <Typography sx={{ flex: 1 }}>{showBill.address}</Typography>
                    </Box>
                    <Box sx={{ display: 'flex' }}>
                      <Typography sx={{ width: '100px', color: '#9ca3af' }}>Contact:</Typography>
                      <Typography sx={{ flex: 1 }}>{showBill.contactNumber}</Typography>
                    </Box>
                  </Box>
                </Box>

                {/* Room Details */}
                <Box sx={{ mb: 3, pb: 2, borderBottom: '1px solid rgba(37, 99, 235, 0.3)' }}>
                  <Typography sx={{ fontSize: '0.9rem', fontWeight: 700, color: '#2563eb', mb: 1 }}>
                    ROOM DETAILS
                  </Typography>
                  <Box sx={{ fontSize: '0.85rem', lineHeight: 1.8 }}>
                    <Box sx={{ display: 'flex' }}>
                      <Typography sx={{ width: '100px', color: '#9ca3af' }}>Room Type:</Typography>
                      <Typography sx={{ flex: 1 }}>{showBill.roomType}</Typography>
                    </Box>
                    <Box sx={{ display: 'flex' }}>
                      <Typography sx={{ width: '100px', color: '#9ca3af' }}>Check-in:</Typography>
                      <Typography sx={{ flex: 1 }}>{showBill.checkInDate}</Typography>
                    </Box>
                    <Box sx={{ display: 'flex' }}>
                      <Typography sx={{ width: '100px', color: '#9ca3af' }}>Check-out:</Typography>
                      <Typography sx={{ flex: 1 }}>{showBill.checkOutDate}</Typography>
                    </Box>
                    <Box sx={{ display: 'flex' }}>
                      <Typography sx={{ width: '100px', color: '#9ca3af' }}>Nights:</Typography>
                      <Typography sx={{ flex: 1 }}>{calculateNights(showBill.checkInDate, showBill.checkOutDate)}</Typography>
                    </Box>
                  </Box>
                </Box>

                {/* Charges */}
                <Box sx={{ mb: 3 }}>
                  <Typography sx={{ fontSize: '0.9rem', fontWeight: 700, color: '#2563eb', mb: 1 }}>
                    CHARGES
                  </Typography>
                  <Box sx={{ fontSize: '0.85rem', lineHeight: 1.8 }}>
                    <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                      <Typography sx={{ color: '#9ca3af' }}>Rate per night:</Typography>
                      <Typography>LKR {({ SINGLE: 5000, DOUBLE: 8000, FAMILY: 12000, SUITE: 20000 }[showBill.roomType] || 0).toLocaleString()}</Typography>
                    </Box>
                    <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                      <Typography sx={{ color: '#9ca3af' }}>Number of nights:</Typography>
                      <Typography>{calculateNights(showBill.checkInDate, showBill.checkOutDate)}</Typography>
                    </Box>
                  </Box>
                </Box>

                {/* Total */}
                <Box sx={{ 
                  pt: 2, 
                  borderTop: '2px solid #2563eb',
                  display: 'flex',
                  justifyContent: 'space-between',
                  alignItems: 'center'
                }}>
                  <Typography sx={{ fontSize: '1.1rem', fontWeight: 700, color: '#2563eb' }}>
                    TOTAL AMOUNT:
                  </Typography>
                  <Typography sx={{ fontSize: '1.3rem', fontWeight: 700, color: '#2563eb' }}>
                    LKR {calculateTotal(showBill.roomType, calculateNights(showBill.checkInDate, showBill.checkOutDate)).toLocaleString()}
                  </Typography>
                </Box>

                {/* Footer */}
                <Box sx={{ textAlign: 'center', mt: 4, pt: 3, borderTop: '1px solid rgba(37, 99, 235, 0.3)' }}>
                  <Typography sx={{ fontSize: '0.85rem', color: '#9ca3af', fontStyle: 'italic' }}>
                    Thank you for choosing Ocean View Resort!
                  </Typography>
                </Box>
              </Box>
            </Paper>
          </DialogContent>
          <DialogActions sx={{ p: 2, background: 'rgba(0, 0, 0, 0.5)' }}>
            <Button onClick={() => window.print()} variant="outlined" sx={{ borderColor: '#2563eb', color: '#2563eb' }}>Print</Button>
            <Button onClick={() => setShowBill(null)} variant="contained" sx={{ 
              background: 'linear-gradient(135deg, #2563eb 0%, #1e3a8a 100%)',
              '&:hover': { background: 'linear-gradient(135deg, #1e3a8a 0%, #2563eb 100%)' }
            }}>
              Close
            </Button>
          </DialogActions>
        </Dialog>
      )}
    </Container>
  )
}
