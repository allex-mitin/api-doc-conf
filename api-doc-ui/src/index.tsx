import {createRoot} from 'react-dom/client';
import '@asyncapi/react-component/styles/default.css';
import 'swagger-ui-react/swagger-ui.css';
import './assets/css/main.css';

import {App} from './components/App';

// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
createRoot(document.getElementById('root')!).render(<App /> );
