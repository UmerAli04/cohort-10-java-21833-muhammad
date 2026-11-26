
import { useEffect, useMemo, useState } from 'react'
import {
  Plus,
  Download,
  CalendarDays,
} from 'lucide-react'
import Sidebar from './components/Sidebar'
import Header from './components/Header'
import DashboardStats from './components/DashboardStats'
import ContactGrowthChart from './components/ContactGrowthChart'
import ContactActivity from './components/ContactActivity'
import RecentContacts from './components/RecentContacts'

const contacts = [
  { id: 1, name: 'Olivia Rhye', email: 'olivia@untitledui.com', phone: '+1 202-555-0142', company: 'Untitled UI', initials: 'OR', color: 'bg-violet-100 text-violet-700', status: 'Active', date: 'Nov 24, 2026' },
  { id: 2, name: 'Phoenix Baker', email: 'phoenix@acme.com', phone: '+1 202-555-0185', company: 'Acme Inc.', initials: 'PB', color: 'bg-orange-100 text-orange-700', status: 'Active', date: 'Nov 23, 2026' },
  { id: 3, name: 'Lana Steiner', email: 'lana@circooles.com', phone: '+1 202-555-0168', company: 'Circooles', initials: 'LS', color: 'bg-pink-100 text-pink-700', status: 'Inactive', date: 'Nov 22, 2026' },
  { id: 4, name: 'Demi Wilkinson', email: 'demi@catalog.com', phone: '+1 202-555-0121', company: 'Catalog', initials: 'DW', color: 'bg-emerald-100 text-emerald-700', status: 'Active', date: 'Nov 21, 2026' },
  { id: 5, name: 'Candice Wu', email: 'candice@layers.com', phone: '+1 202-555-0193', company: 'Layers', initials: 'CW', color: 'bg-sky-100 text-sky-700', status: 'Active', date: 'Nov 20, 2026' },
]

function App() {
  const [activeNav, setActiveNav] = useState('Overview')
  const [search, setSearch] = useState('')
  const [mobileMenu, setMobileMenu] = useState(false)
  const [isDarkMode, setIsDarkMode] = useState(
    () => localStorage.getItem('theme') === 'dark'
  )
  useEffect(() => {
    localStorage.setItem('theme', isDarkMode ? 'dark' : 'light')
  }, [isDarkMode])

  const filteredContacts = useMemo(
    () =>
      contacts.filter((contact) =>
        `${contact.name} ${contact.email} ${contact.company}`
          .toLowerCase()
          .includes(search.toLowerCase()),
      ),
    [search],
  )

  return (
    <div className={`${isDarkMode ? 'dark min-h-screen bg-[#0b1120] text-slate-100' : 'min-h-screen bg-[#f7f8fc] text-slate-900'}`}>
      <div className="flex min-h-screen">
        {/* Sidebar */}
        <Sidebar
          activeNav={activeNav}
          setActiveNav={setActiveNav}
          mobileMenu={mobileMenu}
          setMobileMenu={setMobileMenu}
        />

        {/* Main content */}
        <main className="min-w-0 flex-1">
          {/* Topbar */}
          <Header
            activeNav={activeNav}
            search={search}
            setSearch={setSearch}
            setMobileMenu={setMobileMenu}
            isDarkMode={isDarkMode}
            setIsDarkMode={setIsDarkMode}
          />

          <div className="mx-auto max-w-[1500px] p-5 md:p-9">
            {/* Heading */}
            <div className="flex flex-col justify-between gap-4 sm:flex-row sm:items-center">
              <div>
                <div className="mb-2 flex items-center gap-2">
                  <span className="rounded-full bg-blue-50 px-2.5 py-1 text-[10px] font-bold uppercase tracking-wider text-blue-700">Workspace overview</span>
                  <span className="flex items-center gap-1 text-[11px] font-medium text-emerald-600"><span className="h-1.5 w-1.5 rounded-full bg-emerald-500" /> All systems operational</span>
                </div>
                <h2 className="text-2xl font-bold tracking-tight text-slate-900 md:text-[30px]">Good morning, Umer <span>👋</span></h2>
                <p className="mt-1.5 text-sm text-slate-500">Here’s what’s happening with your contacts today.</p>
              </div>
              <div className="flex gap-2">
                <button className="flex items-center justify-center gap-2 rounded-xl border border-slate-200 bg-white px-4 py-2.5 text-sm font-semibold text-slate-600 shadow-sm hover:bg-slate-50">
                  <Download size={16} /> <span className="hidden sm:inline">Export</span>
                </button>
                <button onClick={() => setActiveNav('Add Contact')} className="flex items-center justify-center gap-2 rounded-xl bg-blue-600 px-4 py-2.5 text-sm font-semibold text-white shadow-lg shadow-blue-200 transition hover:bg-blue-700">
                  <Plus size={17} /> Add contact
                </button>
              </div>
            </div>

            {/* Statistics */}
            <div className="mt-8">
              <DashboardStats
                totalContacts={2450}
                activeContacts={1893}
                newContacts={128}
                inactiveContacts={557}
              />
            </div>

            {/* Analytics */}
            <div className="mt-6 grid gap-5 xl:grid-cols-[1.65fr_1fr]">
              <ContactGrowthChart />
              <ContactActivity />
            </div>

            {/* Contacts table */}
            <RecentContacts
              search={search}
              setSearch={setSearch}
              filteredContacts={filteredContacts}
            />

            <footer className="mt-8 flex flex-col justify-between gap-2 pb-4 text-[11px] text-slate-400 sm:flex-row">
              <p>© 2026 Connectly. All rights reserved.</p>
              <p className="flex items-center gap-1"><CalendarDays size={12} /> Your contacts, beautifully organized.</p>
            </footer>
          </div>
        </main>
      </div>
    </div>
  )
}

export default App
