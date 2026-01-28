import { useState, useEffect } from 'react'

type Account = {
  id: number
  username: string
  email: string
}

function App() {
  // 認証トークン（localStorageから初期値を取得）
  const [token, setToken] = useState<string | null>(localStorage.getItem('token'))

  // ログインフォーム用
  const [loginUsername, setLoginUsername] = useState('')
  const [loginPassword, setLoginPassword] = useState('')
  const [loginError, setLoginError] = useState('')

  // 一覧データ
  const [accounts, setAccounts] = useState<Account[]>([])

  // 登録フォーム用
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')

  // トークン付きで一覧取得
  const fetchAccounts = () => {
    fetch('http://localhost:8080/api/accounts', {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
      .then(res => {
        if (!res.ok) throw new Error('認証エラー')
        return res.json()
      })
      .then(data => setAccounts(data))
      .catch(() => {
        // 認証エラーならログアウト
        handleLogout()
      })
  }

  // ログイン済みの場合のみ一覧取得
  useEffect(() => {
    if (token) {
      fetchAccounts()
    }
  }, [token])

  // ログイン処理
  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoginError('')

    try {
      const res = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: loginUsername, password: loginPassword })
      })

      if (!res.ok) {
        throw new Error('ログイン失敗')
      }

      const data = await res.json()
      localStorage.setItem('token', data.token)
      setToken(data.token)
      setLoginUsername('')
      setLoginPassword('')
    } catch (err) {
      setLoginError('ユーザー名またはパスワードが間違っています')
    }
  }

  // ログアウト処理
  const handleLogout = () => {
    localStorage.removeItem('token')
    setToken(null)
    setAccounts([])
  }

  // トークン付きで登録
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    await fetch('http://localhost:8080/api/accounts', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify({ username, email })
    })

    setUsername('')
    setEmail('')
    fetchAccounts()
  }

  // 未ログインならログイン画面を表示
  if (!token) {
    return (
      <div>
        <h1>ログイン</h1>
        <form onSubmit={handleLogin}>
          <div>
            <label>ユーザー名: </label>
            <input
              type="text"
              value={loginUsername}
              onChange={(e) => setLoginUsername(e.target.value)}
            />
          </div>
          <div>
            <label>パスワード: </label>
            <input
              type="password"
              value={loginPassword}
              onChange={(e) => setLoginPassword(e.target.value)}
            />
          </div>
          {loginError && <p style={{ color: 'red' }}>{loginError}</p>}
          <button type="submit">ログイン</button>
        </form>
      </div>
    )
  }

  // ログイン済みなら一覧画面を表示
  return (
    <div>
      <h1>アカウント一覧</h1>
      <button onClick={handleLogout}>ログアウト</button>

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
