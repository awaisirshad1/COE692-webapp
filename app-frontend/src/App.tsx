import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/login';
import Signup from './components/signup';
import TrainerView from './components/TrainerView';
import './App.css';
import ClientView from './components/ClientView';

function App() {
  return (
    <Router>
      <Routes>
        {/* Set the login route as the default route */}
        <Route path="/" element={<Login />} />        
        <Route path="/signup" element={<Signup />} />
        <Route path="/trainerView" element={<TrainerView />} />
        <Route path="/clientView" element={<ClientView />} />
      </Routes>
    </Router>
  );
}

export default App;
