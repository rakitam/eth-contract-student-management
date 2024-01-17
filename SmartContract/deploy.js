
async function main() {
  const EObrazovanje = await hre.ethers.getContractFactory("EObrazovanje");
  const eObrazovanje = await EObrazovanje.deploy();

  await eObrazovanje.deployed();

  console.log(`EObrazovanje deployed to ${eObrazovanje.address}`);
}

// We recommend this pattern to be able to use async/await everywhere
// and properly handle errors.
main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
