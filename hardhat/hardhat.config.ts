// import { HardhatUserConfig } from "hardhat/config";
// import "@nomicfoundation/hardhat-toolbox";
// require('dotenv').config();

// const config: HardhatUserConfig = {
//   solidity: "0.8.19",
//   networks: {
//     localhost: {
//       url: process.env.LOCALHOST_URL,
//       accounts: ['cd5d82f84ec459700de35df9e2e89e8e9d1feb7c348044bba4eb1c2767cd3cf5'],
//     },
//   },
// };

// export default config;

require("@nomicfoundation/hardhat-toolbox");
require('dotenv').config();

/** @type import('hardhat/config').HardhatUserConfig */
module.exports = {
  solidity: "0.8.19",
  networks: {
    localhost: {
      url: process.env.LOCALHOST_URL,
      accounts: [process.env.PRIVATE_KEY],
    },
  },
};