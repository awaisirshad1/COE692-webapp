import React,{ useState } from "react";
import { Grid, Paper, TextField, Typography, Link as MuiLink, Button, Stack, useTheme, useMediaQuery } from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import SendIcon from '@mui/icons-material/Send';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import { useNavigate } from 'react-router-dom';
import axios, { HttpStatusCode } from "axios";


type LoginFormState = {
  username: string;
  password: string;
};

function Login() {
  const theme = useTheme();
  const navigate = useNavigate();  
  const matches = useMediaQuery(theme.breakpoints.up('md'));

  const[username,setUsername]=useState<string>('');
  const[password,setPassword]=useState<string>('');


  const handleLogin = async(event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault() ;
    const targetUrl = 'http://localhost:8081/accounts/login';
    const loginFormData: LoginFormState = { username, password };

    try {
      // const response = await fetch(url, {
      //   method: 'POST',
      //   headers: {
      //     'Content-Type': 'application/json',
      //   },
      //   body: JSON.stringify(data),
      // });
      const response = axios({
        method:'post',
        url: targetUrl,
        headers:{
          "Access-Control-Allow-Origin":'*',
          "Content-Type":'application/json'
        },
        data: JSON.stringify(loginFormData)

    });

      if ((await response).status == HttpStatusCode.Ok) {
        const result = (await response).data;
        console.log('Login Successful:', result);
        if(result.body ==true){
          navigate('/TrainerView');
        }
        else{
          navigate('/ClientView');
        }
      } else {
        throw new Error('Failed to login');
      }
    } catch (error) {
      console.error('Login Error:', error);
    }
  }

  return (
    <Grid container component="main" sx={{ height: '100vh', overflow: 'hidden' }}>
      {matches && (
        <Grid item md={7} sx={{
          backgroundImage: 'url(boxed-water-is-better-zQNDCje06VM-unsplash.jpg)', // Replace with your image path
          backgroundRepeat: 'no-repeat',
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          position: 'relative',
        }}>
          <Box sx={{
            position: 'absolute',
            bottom: 30,
            left: 30,
            color: 'white',
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            p: 3,
            borderRadius: '4px'
          }}>
            <Typography variant="h5">Welcome Back!</Typography>
            <Typography variant="body2">Please sign in to continue</Typography>
          </Box>
        </Grid>
      )}
      <Grid item xs={12} md={5} component={Paper} elevation={6} square>
        <Stack sx={{ my: 8, mx: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <Box component="form" noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="username"
              label="Username"
              name="username"
              autoComplete="username"
              autoFocus
              variant="outlined"
              value={username} 
              onChange={e => setUsername(e.target.value)} 
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              variant="outlined"
              value={password} 
              onChange={e => setPassword(e.target.value)}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ py: 2, mt: 3, mb: 2 }}
              endIcon={<SendIcon />}
              onClick={handleLogin}
            >
              Sign In
            </Button>
            <Button
              type="reset"
              fullWidth
              variant="outlined"
              sx={{ py: 2, mb: 2 }}
              startIcon={<DeleteIcon />}
            >
              Cancel
            </Button>
            <Grid container justifyContent="space-between">
              <Grid item>
                <MuiLink href="#" variant="body2">
                  Forgot password?
                </MuiLink>
              </Grid>
              <Grid item>
                <MuiLink href="signup" variant="body2">
                  Don't have an account? Sign Up
                </MuiLink>
              </Grid>
            </Grid>
          </Box>
        </Stack>
      </Grid>
    </Grid>
  );
}

export default Login;
