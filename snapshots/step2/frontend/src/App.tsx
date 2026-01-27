import { useState, useEffect } from 'react'

type Account = {
  id: number
  username: string
  email: string
}

function App() {
  // 一覧データ
  const [accounts, setAccounts] = useState<Account[]>([])

  // 入力フォーム用
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')

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

  // 登録処理
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    await fetch('http://localhost:8080/api/accounts', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, email })
    })

    // 入力欄をクリア
    setUsername('')
    setEmail('')

    // 一覧を再取得
    fetchAccounts()
  }

  return (
    <div>
      <h1>アカウント一覧</h1>

      {/* 登録フォーム */}
      <form onSubmit={handleSubmit}>
        <div>
          <label>ユーザー名: </label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div>
          <label>メール: </label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <button type="submit">登録</button>
      </form>

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
