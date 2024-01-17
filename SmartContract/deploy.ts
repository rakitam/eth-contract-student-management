import Web3 from 'web3';
import { deploy } from './EObrazovanje.json';

// Connection to local Ethereum node
const web3 = new Web3('http://localhost:8545');

(async () => {
    try {
      const result = await deploy(web3, 'EObrazovanje', []);
      console.log(`Address: ${result.address}`);
    } catch (e) {
      console.log(e.message);
    }
  })();