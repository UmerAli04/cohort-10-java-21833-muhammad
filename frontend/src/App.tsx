import { useState } from 'react'
import heroImg from './assets/hero.png'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import './App.css'


function App() {
  return (
    <div className="min-h-screen bg-slate-950 flex items-center justify-center p-6">
      <div className="max-w-lg rounded-2xl bg-white p-10 text-center shadow-2xl">
        <h1 className="text-3xl font-bold text-slate-900">
          Contact Management System
        </h1>

        <p className="mt-4 text-slate-600">
          React, TypeScript and Tailwind CSS are ready!
        </p>

        <button className="mt-6 rounded-lg bg-blue-600 px-6 py-3 font-semibold text-white hover:bg-blue-700">
          Setup Verified
        </button>
      </div>
    </div>
  )
}

export default App
