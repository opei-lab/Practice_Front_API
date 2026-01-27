import { useState, useEffect } from 'react'

type Account = {
  id: number
  username: string
  email: string
}

function App() {
  // 一覧データ
  const [accounts, setAccounts] = useState<Account[]>([])

  // 一覧を取得する関数
  const fetchAccounts = () => {
    fetch('http://localhost:8080/api/accounts')
      .then(res => res.json())
      .then(data => setAccounts(data))
  }

  // 初回表示時に一覧取得
  useEffect(() => {
    fetchAccounts()
  }, [])

  return (
    <div>
      <h1>アカウント一覧</h1>

      {/* 一覧表示 */}
      <ul>
        {accounts.map(account => (
          <li key={account.id}>
            {account.username} - {account.email}
          </li>
        ))}
      </ul>
    </div>
  )
}

export default App
